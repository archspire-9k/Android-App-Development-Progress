package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme

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
                    .padding(24.dp)
            )
            Spacer(modifier = Modifier.padding(64.dp))
            RenderNavigation(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun RenderImage(modifier: Modifier = Modifier) {
    Column( horizontalAlignment = Alignment.CenterHorizontally ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            modifier = modifier
        )
        Spacer(modifier = Modifier.padding(16.dp))
        RenderTitleInfo(
            Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(Color.LightGray)
                .padding(16.dp)
        )
    }
}

@Composable
fun RenderTitleInfo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Artwork Title")
        Text(text = "Artwork Artist (year)")
    }
}

@Composable
fun RenderNavigation(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Button(
            onClick = { /*TODO*/ },
        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = { /*TODO*/ },
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