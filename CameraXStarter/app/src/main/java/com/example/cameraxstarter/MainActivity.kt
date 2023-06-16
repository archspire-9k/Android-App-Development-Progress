package com.example.cameraxstarter

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.cameraxstarter.databinding.ActivityMainBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.common.Triangle
import com.google.mlkit.vision.facemesh.FaceMeshDetection
import com.google.mlkit.vision.facemesh.FaceMeshDetector
import com.google.mlkit.vision.facemesh.FaceMeshPoint
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@ExperimentalGetImage
class MainActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityMainBinding

    private lateinit var cameraExecutor: ExecutorService
    private lateinit var defaultDetector: FaceMeshDetector
    private var bounds: Rect = Rect(0,0,0,0)

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        )
        { permissions ->
            // Handle Permission granted/rejected
            var permissionGranted = true
            permissions.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value)
                    permissionGranted = false
            }
            if (!permissionGranted) {
                Toast.makeText(
                    baseContext,
                    "Permission request denied",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startCamera()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        val composeView = viewBinding.composeView
        composeView.setContent {
            CameraView()
        }

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            requestPermissions()
        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        defaultDetector = FaceMeshDetection.getClient(
//            FaceMeshDetectorOptions.Builder()
//                .setUseCase(FaceMeshDetectorOptions.BOUNDING_BOX_ONLY)
//                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()


            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            val faceAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(
                        cameraExecutor
                    ) { imageProxy ->
                        val image = BitmapUtils.getBitmap(imageProxy)
                        if (image != null) {
                            defaultDetector.process(InputImage.fromBitmap(image, 0))
                                .addOnSuccessListener { result ->
                                    // Task completed successfully
                                    if (result != null) {
                                        for (faceMesh in result) {
                                            bounds = faceMesh.boundingBox
                                            Log.d(TAG, "$bounds")
                                            // Gets all points
                                            val faceMeshpoints = faceMesh.allPoints
                                            for (faceMeshpoint in faceMeshpoints) {
                                                val index: Int = faceMeshpoint.index
                                                val position = faceMeshpoint.position
                                            }

                                            // Gets triangle info
                                            val triangles: List<Triangle<FaceMeshPoint>> =
                                                faceMesh.allTriangles
                                            for (triangle in triangles) {
                                                // 3 Points connecting to each other and representing a triangle area.
                                                val connectedPoints = triangle.allPoints
                                            }
                                        }
                                    }
                                    imageProxy.close()
                                }
                                .addOnFailureListener { e ->
                                    // Task failed with an exception
                                    Log.d(TAG, "The result has , $e")
                                }
                        }
                    }
                }
            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, faceAnalyzer
                )

            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun requestPermissions() {
        activityResultLauncher.launch(REQUIRED_PERMISSIONS)
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val TAG = "CameraXApp"

        //        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}