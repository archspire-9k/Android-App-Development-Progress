package com.example.cameraxstarter

import android.graphics.Rect
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cameraxstarter.ui.theme.CameraXStarterTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CameraView(modifier: Modifier = Modifier, rect: Rect = Rect(0, 0, 0, 0)) {

    CameraXStarterTheme {
        // A surface container using the 'background' color from the theme
        val brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue))
        Canvas(
            modifier = Modifier.size(100.dp)
        ) {
            drawCircle(brush)
        }

    }
}