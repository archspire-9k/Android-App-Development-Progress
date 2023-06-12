package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

data class Info (@DrawableRes val image:  Int, val title: String, val artist: String)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtDisplay()
            }
        }
    }
}

@Composable
fun ArtDisplay() {
    var imageIndex by remember {
        mutableStateOf(0)
    }

    val array = arrayOf(
        Info( R.drawable.ic_launcher_background, "Background", "First Image (2023)"),
        Info( R.drawable.ic_launcher_foreground, "Foreground", "Second Image (2023)")
        )

    val resource = when(imageIndex) {
        0 -> array[0]
        1 -> array[1]
        else -> array[0]
    }
    Surface(
        modifier = Modifier.fillMaxHeight(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.wrapContentSize(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            RenderImage(
                Modifier
                    .background(Color.Yellow)
                    .padding(24.dp),
                resource
            )
            Spacer(modifier = Modifier.padding(64.dp))
            RenderNavigation(
                modifier = Modifier.fillMaxWidth(),
                onPrev = { if( imageIndex > 0 ) imageIndex-- },
                onNext = { if( imageIndex < 1 ) imageIndex++ })
        }
    }
}

@Composable
fun RenderImage(modifier: Modifier = Modifier, resource: Info) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            painter = painterResource(id = resource.image),
            contentDescription = null,
            modifier = modifier
        )
        Spacer(modifier = Modifier.padding(16.dp))
        RenderTitleInfo(
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(16.dp),
            resource.artist, resource.title
        )
    }
}

@Composable
fun RenderTitleInfo(modifier: Modifier = Modifier, artist: String, title: String) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title)
        Text(text = artist)
    }
}

@Composable
fun RenderNavigation(modifier: Modifier = Modifier, onPrev: () -> Unit, onNext: () -> Unit) {
    Row(
        modifier = modifier.padding(bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Button(
            onClick = onPrev,
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = onNext,
        ) {
            Text(text = "Next")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        ArtDisplay()
    }
}