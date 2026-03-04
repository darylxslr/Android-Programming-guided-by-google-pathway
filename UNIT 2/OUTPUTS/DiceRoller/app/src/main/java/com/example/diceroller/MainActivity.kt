package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DiceRollerApp()
                }
            }
        }
    }
}

@Preview
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier) {

    var result by remember { mutableStateOf(1) }
    var rotation by remember { mutableStateOf(0f) }
    var history by remember { mutableStateOf(listOf<Int>()) }

    val animatedRotation by animateFloatAsState(
        targetValue = rotation,
        animationSpec = tween(durationMillis = 500),
        label = "diceRotation"
    )

    val imageResource = when (result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }

    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(imageResource),
                contentDescription = result.toString(),
                modifier = Modifier.graphicsLayer {
                    rotationZ = animatedRotation
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {

                    val newRoll = (1..6).random()
                    result = newRoll

                    history = history + newRoll

                    val direction = if ((0..1).random() == 0) -1 else 1
                    val spins = (1..3).random()

                    rotation += 360f * spins * direction
                }
            ) {
                Text(
                    text = stringResource(R.string.roll),
                    fontSize = 24.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
        ) {

            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Text(
                    text = "History",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("-------------------------------------------------------")

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (history.isEmpty()) "No rolls yet"
                    else history.joinToString(", "),
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("-------------------------------------------------------")
            }
        }

        Text(
            text = buildAnnotatedString {
                append("by ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("Daryl Lacorte")
                }
            },
            fontSize = 16.sp
        )
    }
}