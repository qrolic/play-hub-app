@file:OptIn(ExperimentalMaterial3Api::class)

package com.qrolic.minesweeper.activity.splash.screen.tictactoe

import android.app.Activity
import android.os.Looper
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.activity.splash.Screen
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.isGameOver
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.isPlayer1Win
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.isPlayer2Win
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.player
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.player1
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.player2
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToeBoard.Companion.turn
import com.qrolic.minesweeper.ui.theme.NotoSans
import com.qrolic.minesweeper.utils.playSoundClick

var count = 0

@SuppressWarnings("kotlin:S3776")
// cognitive complexity
@Composable
fun TicTacToe(navController: NavController, boardSize: String, type: String, activity: Activity) {
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
                                activity.playSoundClick()
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
                        .background(/*if (type=="Friend" && turn.value == "Player 1") */
                            MaterialTheme.colorScheme.primary /*else Color.Black*/
                        )
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    icon = R.drawable.boy,
                    title = player1Title,
                    symbol = R.drawable.x
                )
                PlayerTurn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(10.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(/*if (type=="Friend" && turn.value == "Player 2")*/
                            MaterialTheme.colorScheme.tertiary /*else Color.Black*/
                        )
                        .padding(vertical = 10.dp, horizontal = 15.dp),
                    icon = player2Icon,
                    title = player2Title,
                    symbol = R.drawable.o
                )
            }
            Board(boardSize.toInt(), type)
        }
    }
    val context = LocalContext.current

    if (isPlayer1Win.value || isPlayer2Win.value || isGameOver.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            if (isPlayer1Win.value) {
                if (type == "AI") {
                    WinDialog(
                        stringResource(id = R.string.congratulation),
                        "You Won",
                        R.drawable.boy,
                        R.raw.trophy,
                        continueClick = {
                            context.playSoundClick()
                            navController.popBackStack()
                            navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize")
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
                            navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize")
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
                            navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize")
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
                            navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize")
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
                        navController.navigate(Screen.TicTacToe.route + "/$type" + "/$boardSize")
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
            Celebration(Modifier.fillMaxSize(), R.raw.celebration)

        }
    }

}


@Composable
fun PlayerTurn(
    modifier: Modifier,
    icon: Int,
    title: String,
    symbol: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "",
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .align(CenterVertically)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontFamily = NotoSans,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center
            )
            Image(
                painter = painterResource(id = symbol),
                contentDescription = "",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .padding(5.dp)
            )
        }
    }
}


@Composable
fun Board(boardSize: Int, type: String) {
    // 2D array is created for keep track of cell
    // We store "X" or "O" if cell has any value according to player1 and player2
    // if "" it means cell is empty
    val symbols =
        Array(boardSize) { Array(boardSize) { Symbol(mutableStateOf(0), mutableStateOf(false)) } }
    count = 0
    player = player1
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
                                TicTacToeButton(
                                    row,
                                    col,
                                    boardSize,
                                    type,
                                    symbols
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
fun TicTacToeButton(
    row: Int,
    col: Int,
    boardSize: Int,
    type: String,
    symbols: Array<Array<Symbol>>,
) {
    val context = LocalContext.current
    val board = TicTacToeBoard()
    var size = 80.dp
    if (boardSize == 5 || boardSize == 6) {
        size = 55.dp
    }
    IconButton(
        onClick = {
            context.playSoundClick()
            if (type == "AI") {
                if (symbols[row][col].symbol.value == 0) {
                    // set symbol value
                    symbols[row][col].symbol.value = player

                    count++
                    // check is any win or not
                    board.state(
                        context,
                        symbols,
                        row,
                        col,
                        boardSize,
                        player

                    )
                    if (!isPlayer1Win.value) {
                        player = player2
                        // Computer/AI Code
                        //delay
                        android.os.Handler(Looper.getMainLooper()).postDelayed({
                            // check AI/Computer wins in next move
                            val isAIWin =
                                board.checkAIWinInNextMove(symbols, boardSize, player)
                            if (isAIWin != null) {
                                symbols[isAIWin[0]][isAIWin[1]].symbol.value = player
                                board.state(
                                    context,
                                    symbols,
                                    isAIWin[0],
                                    isAIWin[1],
                                    boardSize,
                                    player

                                )
                                context.playSoundClick()
                            } else {
                                // check player1 can win in next move
                                val isPlayer1Win =
                                    board.checkAIWinInNextMove(symbols, boardSize, player1)
                                if (isPlayer1Win != null) {
                                    symbols[isPlayer1Win[0]][isPlayer1Win[1]].symbol.value = player
                                    count++
                                    board.state(
                                        context,
                                        symbols,
                                        isPlayer1Win[0],
                                        isPlayer1Win[1],
                                        boardSize,
                                        player

                                    )
                                    player = player1
                                    context.playSoundClick()
                                } else {
                                    // get random position
                                    val rowCol = board.getRandomRowCol(symbols, boardSize)
                                    if (rowCol != null) {
                                        symbols[rowCol[0]][rowCol[1]].symbol.value = player
                                        count++
                                        context.playSoundClick()
                                    }
                                    board.state(
                                        context,
                                        symbols,
                                        rowCol?.get(0) ?: 0,
                                        rowCol?.get(1) ?: 0,
                                        boardSize,
                                        player
                                    )
                                    player = player1
                                }
                            }
                        }, 500)
                    }
                }
            } else {
                // multiplayer code
                if (symbols[row][col].symbol.value == 0) {
                    if (player == player1) {
                        turn.value = "Player 2"
                    } else {
                        turn.value = "Player 1"
                    }
                    // set symbol value
                    symbols[row][col].symbol.value = player
                    count++
                    // change player's turn
                    player = if (player == player1) {
                        // now cell contains symbol
                        symbols[row][col].symbol.value = player
                        // check is any win or not
                        board.state(
                            context,
                            symbols,
                            row,
                            col,
                            boardSize,
                            player
                        )
                        player2
                    } else {
                        // now cell contains symbol
                        symbols[row][col].symbol.value = player
                        // check is any win or not
                        board.state(
                            context,
                            symbols,
                            row,
                            col,
                            boardSize,
                            player
                        )
                        player1
                    }
                }
            }
        },
        modifier = Modifier
            .background(if (symbols[row][col].isWinPos.value) Color.Yellow else Color.Black)
            .border(width = 0.5.dp, color = MaterialTheme.colorScheme.primary)
            .size(size)
    ) {

        if (symbols[row][col].symbol.value != 0) {
            Image(
                painter = painterResource(id = symbols[row][col].symbol.value),
                contentDescription = "player",
                modifier = Modifier.padding(10.dp),
            )
        }
    }
}

