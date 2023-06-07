package com.example.learntogether

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learntogether.ui.theme.LearnTogetherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearnTogetherTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        verticalArrangement = Arrangement.Top
                    ) {
                        HeaderImage()
                        TitleText()
                        BodyText()
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderImage() {
    val painter = painterResource(R.drawable.bg_compose_background)
    Image(painter = painter, contentDescription = null, contentScale = ContentScale.FillWidth)
}

@Composable
fun TitleText() {
    Text(
        text = stringResource(R.string.title),
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun BodyText() {
        Text(
            text = stringResource(R.string.body1),
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp
            ),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(R.string.body2),
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun GreetingPreview() {
    LearnTogetherTheme {
        HeaderImage()
        TitleText()
        BodyText()
    }
}