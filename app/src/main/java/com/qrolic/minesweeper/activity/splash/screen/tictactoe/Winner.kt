package com.qrolic.minesweeper.activity.splash.screen.tictactoe

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.airbnb.lottie.compose.*
import com.qrolic.minesweeper.ui.theme.NotoSans


@Composable
fun WinDialog(
    title: String,
    winnerName: String, icon: Int,
    animation: Int,
    continueClick: () -> Unit,
    homeClick: () -> Unit
) {

    Dialog(onDismissRequest = {}) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(rememberScrollState(), Orientation.Vertical, true)

                .clip(RoundedCornerShape(25.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(horizontal = 15.dp, vertical = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .size(80.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp)
            )
            Text(
                text = title,
                fontFamily = NotoSans,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )

            Text(
                text = winnerName,
                fontFamily = NotoSans,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            Celebration(modifier = Modifier.size(250.dp), raw = animation)
            Row(
                modifier = Modifier.padding(top = 25.dp)
            ) {
                Button(
                    onClick = homeClick, modifier = Modifier
                        .padding(10.dp)
                        .weight(1f), colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "Home",
                        fontFamily = NotoSans,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                    )
                }
                Button(
                    onClick = continueClick, modifier = Modifier
                        .padding(10.dp)
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = "Continue",
                        fontFamily = NotoSans,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                    )
                }
            }

        }
    }
}

@Composable
fun Celebration(modifier: Modifier, raw: Int) {
    val compositionResult1: LottieCompositionResult =
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(raw))
    val progress1 by animateLottieCompositionAsState(
        composition = compositionResult1.value,
        isPlaying = true,
        iterations = LottieConstants.IterateForever,
        speed = 1.0f
    )
    LottieAnimation(
        composition = compositionResult1.value,
        progress = progress1,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}