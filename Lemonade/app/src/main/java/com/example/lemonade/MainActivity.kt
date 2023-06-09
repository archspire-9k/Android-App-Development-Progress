package com.example.lemonade

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lemonade.ui.theme.LemonadeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                LemonApp()
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {

        return false
    }

    override fun onStart() {
        super.onStart()
        val activityManager: ActivityManager =
            getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager.lockTaskModeState == ActivityManager.LOCK_TASK_MODE_NONE) {
            startLockTask()
        }
    }
}

@Composable
fun ImageAndText(
    textResource: String,
    imageResource: Int,
    step: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var count = 0
    if (step == 2) {
        count = (2..4).random()
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(
            onClick = {
                if (count > 0) {
                    count -= 1
                    Log.d("SQUEEZE COUNT", "$count")
                } else {
                    onClick()
                }
            },
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xffd2e7d4))
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = "lemon_tree",
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = textResource)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LemonApp() {
    var step by remember { mutableStateOf(1) }
    val imageResource = when (step) {
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    val textResource = when (step) {
        1 -> stringResource(R.string.lemon_tree)
        2 -> stringResource(R.string.lemon_squeeze)
        3 -> stringResource(R.string.lemon_drink)
        else -> stringResource(R.string.lemon_restart)
    }


//   Scaffold(
//        topBar = {
//            CenterAlignedTopAppBar(
//                title = {
//                    Text(
//                        text = "Lemonade",
//                        fontWeight = FontWeight.Bold
//                    )
//                },
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
//                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
//                )
//            )
//        }
//    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
//                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = MaterialTheme.colorScheme.background
        ) {
            ImageAndText(
                textResource,
                imageResource,
                step,
                {
                    if (step in 1..3) {
                        step++
                    } else {
                        step = 1
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
//}