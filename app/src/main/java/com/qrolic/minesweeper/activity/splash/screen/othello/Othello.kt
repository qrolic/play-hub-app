@file:OptIn(ExperimentalMaterial3Api::class)

package com.qrolic.minesweeper.activity.splash.screen.othello

import android.app.Activity
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.activity.splash.Screen
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.count
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.isGameOver
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.isPlayer1Win
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.isPlayer2Win
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.player
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.player1Symbol
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloBoard.Companion.player2Symbol
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.Celebration
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.PlayerTurn
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.WinDialog
import com.qrolic.minesweeper.utils.playSoundClick

const val boardSize = 8

@SuppressWarnings("kotlin:S3776")
// cognitive complexity
@Composable
fun Othello(navController: NavController, type: String, activity: Activity) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        topBar = {
            SmallTopAppBar(
                title = {},
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
                                activity.onBackPressed()
                            },
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            )
        }
    ) {

        Column(
            modifier = Modifier.background(MaterialTheme.colorScheme.secondary)
        ) {
            val player1Title = if (type == "AI") {
                "ME"
            } else {
                "Player 1"
            }
            var player2Icon = R.drawable.ai
            var player2Title = "AI"
            if (type != "AI") {
                player2Title = "Player 2"
                player2Icon = R.drawable.boy2
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
            ) {
                PlayerTurn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    icon = R.drawable.boy,
                    title = player1Title,
                    symbol = R.drawable.white_circle
                )
                PlayerTurn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    icon = player2Icon,
                    title = player2Title,
                    symbol = R.drawable.black_circle
                )
            }
            Board(type)
        }
    }
    val context = LocalContext.current
    if (isPlayer1Win.value || isPlayer2Win.value || isGameOver.value) {

        Celebration(Modifier.fillMaxSize(), R.raw.celebration)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isPlayer1Win.value) {
                    if (type == "AI") {
                        WinDialog(stringResource(R.string.congratulation),
                            "You Won",
                            R.drawable.boy,
                            R.raw.trophy,
                            continueClick = {
                                context.playSoundClick()
                                navController.popBackStack()
                                navController.navigate(Screen.Othello.route + "/$type")
                                isPlayer1Win.value = false
                                isPlayer2Win.value = false
                                isGameOver.value = false
                            }
                        ) {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.MainScreen.route)
                            isPlayer1Win.value = false
                            isPlayer2Win.value = false
                            isGameOver.value = false
                        }
                    } else {
                        WinDialog(
                            "Congratulations !",
                            "Player 1 Won",
                            R.drawable.boy,
                            R.raw.trophy,
                            continueClick = {
                                context.playSoundClick()
                                navController.popBackStack()
                                navController.navigate(Screen.Othello.route + "/$type")
                                isPlayer1Win.value = false
                                isPlayer2Win.value = false
                                isGameOver.value = false
                            }
                        ) {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.MainScreen.route)
                            isPlayer1Win.value = false
                            isPlayer2Win.value = false
                            isGameOver.value = false
                        }
                    }
                } else if (isPlayer2Win.value) {
                    if (type == "AI") {
                        WinDialog(
                            "Try agin !",
                            "AI Won",
                            R.drawable.ai,
                            R.raw.trophy,
                            continueClick = {
                                context.playSoundClick()
                                navController.popBackStack()
                                navController.navigate(Screen.Othello.route + "/$type")
                                isPlayer1Win.value = false
                                isPlayer2Win.value = false
                                isGameOver.value = false
                            }
                        ) {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.MainScreen.route)
                            isPlayer1Win.value = false
                            isPlayer2Win.value = false
                            isGameOver.value = false
                        }
                    } else {
                        WinDialog(
                            "Congratulations !",
                            "Player 2 Won",
                            R.drawable.boy2,
                            R.raw.trophy,
                            continueClick = {
                                context.playSoundClick()
                                navController.popBackStack()
                                navController.navigate(Screen.Othello.route + "/$type")
                                isPlayer1Win.value = false
                                isPlayer2Win.value = false
                                isGameOver.value = false
                            }
                        ) {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.MainScreen.route)
                            isPlayer1Win.value = false
                            isPlayer2Win.value = false
                            isGameOver.value = false
                        }
                    }
                } else {
                    WinDialog(
                        "Try agin !",
                        "Game Over",
                        R.drawable.boy,
                        R.raw.sad_star,
                        continueClick = {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.Othello.route + "/$type")
                            isPlayer1Win.value = false
                            isPlayer2Win.value = false
                            isGameOver.value = false
                        }
                    ) {
                        context.playSoundClick()
                        navController.popBackStack()
                        navController.navigate(Screen.MainScreen.route)
                        isPlayer1Win.value = false
                        isPlayer2Win.value = false
                        isGameOver.value = false
                    }
                }

            }
    }
}

@Composable
fun Board(type: String) {
    // initialize array
    val symbols =
        Array(boardSize) { Array(boardSize) { mutableStateOf(0) } }
    symbols[3][3].value = player1Symbol
    symbols[3][4].value = player2Symbol
    symbols[4][3].value = player2Symbol
    symbols[4][4].value = player1Symbol
    player = player1Symbol
    count = 0

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.secondary),
        horizontalArrangement = Arrangement.Center
    ) {
        item {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    repeat((1..boardSize).count()) { row: Int ->
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            repeat((1..boardSize).count()) { col: Int ->
                                BoardItem(
                                    row,
                                    col,
                                    symbols,
                                    type
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}

@SuppressWarnings("kotlin:S3776")
// cognitive complexity
@Composable
fun BoardItem(
    row: Int,
    col: Int,
    symbols: Array<Array<MutableState<Int>>>,
    type: String
) {
    val context = LocalContext.current
    val othelloBoard = OthelloBoard()

    IconButton(
        onClick = {
            context.playSoundClick()
            if (symbols[row][col].value == 0) {
                if (type == "AI") {
                    // move fun
                    var isMoved = false
                    if (othelloBoard.move(row, col, symbols, player1Symbol)) {
                        isMoved = true
                        count++

                        if (count == boardSize * boardSize) {
                            isMoved = false
                            // find winner
                            val state = othelloBoard.checkWinnerState(symbols)
                            othelloBoard.stateNavigation(state, context)
                        }
                    }

                    // check is game complete or incomplete
                    val state = othelloBoard.state(symbols, player2Symbol)
                    othelloBoard.stateNavigation(state, context)

                    if (isMoved) {
                        // change turn
                        player = if (player == player1Symbol) {
                            player2Symbol
                        } else {
                            player1Symbol
                        }

                        // Computer/AI Code
                        //delay
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            val rowCol = othelloBoard.getRandomRowCol(symbols, player2Symbol)
                            if (rowCol != null) {
                                othelloBoard.move(rowCol[0], rowCol[1], symbols, player2Symbol)
                                count++

                                if (count == boardSize * boardSize) {
                                    isMoved = false
                                    // find winner
                                    val stateWin = othelloBoard.checkWinnerState(symbols)
                                    othelloBoard.stateNavigation(stateWin, context)
                                }

                                // check is game complete or incomplete
                                val state1 = othelloBoard.state(symbols, player1Symbol)
                                othelloBoard.stateNavigation(state1, context)

                                // change turn
                                player = if (player == player1Symbol) {
                                    player2Symbol
                                } else {
                                    player1Symbol
                                }
                                context.playSoundClick()
                            }
                        }, 500)

                    }

                } else {
                    // move fun
                    var isMoved = false
                    if (othelloBoard.move(row, col, symbols, player)) {
                        isMoved = true
                        count++

                        if (count == boardSize * boardSize) {
                            isMoved = false
                            // find winner
                            val state = othelloBoard.checkWinnerState(symbols)
                            othelloBoard.stateNavigation(state, context)
                        }
                    }
                    if (isMoved) {
                        // change turn
                        player = if (player == player1Symbol) {
                            player2Symbol
                        } else {
                            player1Symbol
                        }
                    }

                    // check is game complete or incomplete
                    val state = othelloBoard.state(symbols, player)
                    othelloBoard.stateNavigation(state, context)


                }
            }
        },
        modifier = Modifier.size(45.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.wood),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
        if (symbols[row][col].value != 0) {
            Image(
                painter = painterResource(id = symbols[row][col].value),
                contentDescription = "",
                modifier = Modifier.size(30.dp),
            )
        }
    }

}




