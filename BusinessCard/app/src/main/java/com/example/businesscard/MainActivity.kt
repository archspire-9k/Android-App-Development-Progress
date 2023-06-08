package com.example.businesscard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize().background(Color.DarkGray),
                    color = MaterialTheme.colorScheme.secondary
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val weight = Modifier.weight(1f)
                        ProfileComponent(Modifier.weight(2f))
                        ContactInfo("123", "github.com", "email@sample.com", weight)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoBox(icon : ImageVector, text: String, modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(
            bottom = 8.dp,
            top = 4.dp,
            start = 4.dp,
            end = 4.dp
        )
    ) {
        Icon(imageVector = icon, contentDescription = text, tint = Color(0xff195f3a), modifier = Modifier.padding(end = 16.dp))
        Text(text = text)
    }
}

@Composable
fun ProfileComponent(modifier : Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.android_logo),
            contentDescription = "Profile Icon",
            modifier = Modifier
                .size(200.dp)
                .background(Color(0xff073042)),
            colorFilter = ColorFilter.tint(Color(0xff3ddb84))
        )
        Text(
            text = "John Doe",
            fontSize = 45.sp,
            lineHeight = 60.sp
        )
        Text(
            text = "Android Developer",
            fontWeight = FontWeight.Bold,
            color = Color(0xff096437)
        )
    }
}

@Composable
fun ContactInfo(phone: String, link: String, mail: String, modifier: Modifier = Modifier) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            modifier = modifier.padding(bottom = 36.dp)
        ) {
            val appIcons = Icons.Rounded
            InfoBox(icon = appIcons.Phone, text = phone)
            InfoBox(icon = appIcons.Share, text = link)
            InfoBox(icon = appIcons.Email, text = mail)
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    BusinessCardTheme {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            val weight = Modifier.weight(1f)
            ProfileComponent(Modifier.weight(2f))
            ContactInfo("+1 234 931 9865", "github.com/john_doe", "email@sample.com", weight)
        }
    }
}