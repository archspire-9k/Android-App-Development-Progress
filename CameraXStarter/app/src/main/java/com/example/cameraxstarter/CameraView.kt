package com.example.cameraxstarter

import android.content.Context
import android.graphics.Rect
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.facemesh.FaceMeshDetector
import java.util.concurrent.ExecutorService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.math.abs

@ExperimentalGetImage
@Composable
fun CameraView(executor: ExecutorService, defaultDetector: FaceMeshDetector) {
    var bounds by remember { mutableStateOf(Rect(0, 0, 0, 0)) }
    var faceMeshpoints by remember {
        mutableStateOf(listOf(Offset(0f, 0f)))
    }
    var screenHeightPx: Float
    var screenWidthPx: Float
    var scaleHeight: Float = 1f
    var scaleWidth: Float = 1f
    val lensFacing = CameraSelector.LENS_FACING_FRONT
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val builder = Preview.Builder()
//    val targetResolution = PreferenceUtils
    val preview = builder.build()
    val previewView = remember { PreviewView(context) }
    val imageAnalysis: ImageAnalysis = remember {
        ImageAnalysis.Builder().build().also {
            it.setAnalyzer(
                executor
            ) { imageProxy ->
                val image = BitmapUtils.getBitmap(imageProxy)
                if (image != null) {
                    defaultDetector.process(InputImage.fromBitmap(image, 0))
                        .addOnSuccessListener { result ->
                            // Task completed successfully
                            if (result != null) {
                                for (faceMesh in result) {
                                    bounds = faceMesh.boundingBox
//                                    Log.d("FAIL", "$bounds")
                                    // Gets all points
                                    faceMeshpoints = faceMesh.allPoints.map { pair ->
                                        Offset(
                                            pair.position.x * scaleWidth * abs(pair.position.z),
                                            pair.position.y * scaleHeight * abs(pair.position.z)
                                        )
                                    }


//                                    // Gets triangle info
//                                    val triangles: List<Triangle<FaceMeshPoint>> =
//                                        faceMesh.allTriangles
//                                    for (triangle in triangles) {
//                                        // 3 Points connecting to each other and representing a triangle area.
//                                        val connectedPoints = triangle.allPoints
//                                    }
                                }
                            }
                            imageProxy.close()
                        }.addOnFailureListener { e ->
                            // Task failed with an exception
                            Log.d("FAIL", "The result has , $e")
                        }
                }
            }
        }
    }
    val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

    // 2
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner, cameraSelector, preview, imageAnalysis
        )
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    // 3
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        val density = LocalDensity.current
        val configuration = LocalConfiguration.current
        screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
        screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }
        scaleHeight = screenHeightPx / 640
        scaleWidth = screenWidthPx / 480
        Log.d("RATIO", "Composable ratio ${screenHeightPx / screenWidthPx}")
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())
        Log.d("POINTS", "$faceMeshpoints")
        Canvas(
            Modifier.fillMaxSize()
        ) {
            drawRect(
                color = Color.Red, topLeft = Offset(
                    bounds.left.toFloat() * scaleWidth

                            * 0.84f, bounds.top.toFloat() * scaleHeight * 1.18f
                ), size = Size(
                    bounds.width().toFloat() * scaleWidth * 1.3f,
                    bounds.height().toFloat() * scaleHeight * 0.9f
                ), style = Stroke(width = 2f)
            )
            faceMeshpoints.forEach {
                drawCircle(
                    color = Color.White, radius = 4f, center = it
                )
            }
        }

    }


}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }