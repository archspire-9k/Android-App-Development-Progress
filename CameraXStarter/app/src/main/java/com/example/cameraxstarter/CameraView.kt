package com.example.cameraxstarter

import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.cameraxstarter.ui.theme.CameraXStarterTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraView(rect: Rect = Rect(0,0,0,0), modifier: Modifier = Modifier) {

            CameraXStarterTheme {
                // A surface container using the 'background' color from the theme
                Canvas(modifier = modifier) {
                    Rect(88, 220, - 360, 585)
                }
            }
}