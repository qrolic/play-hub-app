@file:OptIn(ExperimentalMaterial3Api::class)

package com.qrolic.minesweeper.activity.splash.screen.othello

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.activity.splash.Screen
import com.qrolic.minesweeper.ui.theme.NotoSans
import com.qrolic.minesweeper.utils.playSoundClick

@Composable
fun OthelloSelectGameType(
    navController: NavController,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.othello_logo),
                contentDescription = "app icon",
                modifier = Modifier
                    .size(250.dp)
                    .padding(15.dp)
                    .align(Alignment.CenterHorizontally),
            )
            val type1 = "AI"
            Button(
                onClick = {
                    context.playSoundClick()
                    navController.navigate(Screen.Othello.route + "/$type1")
                }, modifier = Modifier.padding(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text(
                    text = "Play With AI",
                    fontFamily = NotoSans,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
            }
            val type2 = "Friend"
            Button(onClick = {
                context.playSoundClick()
                navController.navigate(Screen.Othello.route + "/$type2")
            }, modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Play With Friend",
                    fontFamily = NotoSans,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}