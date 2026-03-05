package com.example.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

@Composable
fun ArtSpaceApp() {
    var currentStep by remember { mutableStateOf(1) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (currentStep) {
            1 -> ArtSpaceScreen(
                drawableResourceId = R.drawable.artwork1,
                title = "Salvator Mundi",
                artist = "Leonardo di ser Piero da Vinci",
                year = "1500",
                onPreviousClick = { currentStep = 3 },
                onNextClick = { currentStep = 2 }
            )
            2 -> ArtSpaceScreen(
                drawableResourceId = R.drawable.artwork2,
                title = "Saturn Devouring His Son",
                artist = "Francisco Goya",
                year = "1823",
                onPreviousClick = { currentStep = 1 },
                onNextClick = { currentStep = 3 }
            )
            3 -> ArtSpaceScreen(
                drawableResourceId = R.drawable.artwork3,
                title = "Hypertrichosis (Werewolf Syndrome)",
                artist = "Antonietta Gonsalvus",
                year = "around 1595",
                onPreviousClick = { currentStep = 2 },
                onNextClick = { currentStep = 1 }
            )
        }
    }
}

@Composable
fun ArtSpaceScreen(
    drawableResourceId: Int,
    title: String,
    artist: String,
    year: String,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(16.dp)
                    .shadow(elevation = 8.dp),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = title,
                    modifier = Modifier.padding(16.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.width(24.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFFE5B4)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = title, fontSize = 24.sp, fontWeight = FontWeight.Light)
                        Row {
                            Text(text = artist, fontWeight = FontWeight.Bold)
                            Text(text = " ($year)")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = onPreviousClick, modifier = Modifier.weight(1f)) {
                        Text("Previous")
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(onClick = onNextClick, modifier = Modifier.weight(1f)) {
                        Text("Next")
                    }
                }
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
                    .shadow(elevation = 8.dp),
                color = Color.White
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = title,
                    modifier = Modifier.padding(32.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                color = Color(0xFFFFE5B4)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Light
                    )
                    Row {
                        Text(
                            text = artist,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = " ($year)"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onPreviousClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Previous")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = onNextClick,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Next")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}