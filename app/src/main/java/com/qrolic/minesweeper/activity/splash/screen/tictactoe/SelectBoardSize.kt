@file:OptIn(ExperimentalMaterial3Api::class)

package com.qrolic.minesweeper.activity.splash.screen.tictactoe

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qrolic.minesweeper.activity.splash.Screen
import com.qrolic.minesweeper.ui.theme.NotoSans
import com.qrolic.minesweeper.utils.playSoundClick

@Composable
fun SelectBoardSize(navController: NavController, type: String, activity: Activity) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {

                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Arrow left",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(start = 15.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
                            .clickable {
                                context.playSoundClick()
                                activity.onBackPressed()
                            },
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val boardSize1 = "3"
                Button(
                    onClick = {
                        context.playSoundClick()
                        navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize1")
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(150.dp)
                ) {
                    Text(
                        text = "3 X 3", modifier = Modifier.padding(10.dp), fontFamily = NotoSans,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                    )
                }
                val boardSize2 = "4"
                Button(
                    onClick = {
                        context.playSoundClick()
                        navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize2")
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "4 X 4", modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = NotoSans
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                val boardSize3 = "5"
                Button(
                    onClick = {
                        context.playSoundClick()
                        navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize3")
                    },
                    modifier = Modifier
                        .padding(10.dp)
                        .size(150.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = "5 X 5", modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = NotoSans
                    )
                }
                val boardSize4 = "6"
                Button(
                    onClick = {
                        context.playSoundClick()
                        navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize4")
                    }, modifier = Modifier
                        .padding(10.dp)
                        .size(150.dp)
                ) {
                    Text(
                        text = "6 X 6", modifier = Modifier.padding(10.dp),
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = NotoSans
                    )
                }
            }
        }
    }
}