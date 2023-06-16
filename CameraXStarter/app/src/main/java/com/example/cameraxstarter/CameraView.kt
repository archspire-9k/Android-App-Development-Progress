package com.example.cameraxstarter

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.cameraxstarter.ui.theme.CameraXStarterTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraView(modifier: Modifier = Modifier) {

            CameraXStarterTheme {
                // A surface container using the 'background' color from the theme
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasQuadrantSize = size / 2F
                    drawRect(
                        color = Color.Magenta,
                        size = canvasQuadrantSize
                    )
                }
            }
}