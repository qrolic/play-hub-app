package com.qrolic.minesweeper.activity.splash.screen.tictactoe

import android.content.Context
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.utils.playSoundWin

data class WinPos(
    val row: Int,
    val col: Int
)

data class Symbol(
    val symbol: MutableState<Int>,
    val isWinPos: MutableState<Boolean>
)

class TicTacToeBoard {

    companion object {
        const val player1 = R.drawable.x
        const val player2 = R.drawable.o
        const val player1_win = 1
        const val player2_win = 2
        const val draw = 3
        const val incomplete = 4
        var player = player1
        var isPlayer1Win = mutableStateOf(false)
        var isPlayer2Win = mutableStateOf(false)
        var isGameOver = mutableStateOf(false)
        var turn = mutableStateOf("Player 1")
        var winList = mutableListOf<WinPos>()
    }

    fun getRandomRowCol(symbols: Array<Array<Symbol>>, boardSize: Int): IntArray? {
        val tempList = arrayListOf<String>()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (symbols[i][j].symbol.value == 0) {
                    tempList.add("$i$j")
                }
            }
        }

        if (tempList.isEmpty()) {
            return null
        }
        val temp = tempList.random()

        return intArrayOf(temp.substring(0, 1).toInt(), temp.substring(1).toInt())
    }

    fun state(
        context: Context,
        matrix: Array<Array<Symbol>>,
        row: Int,
        col: Int,
        boardSize: Int,
        player: Int

    ) {
        when (move(matrix, row, col, boardSize, player)) {
            1 -> {
                context.playSoundWin()
                for (i in winList) {
                    matrix[i.row][i.col].isWinPos.value = true
                }
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isPlayer1Win.value = true
                }, 500)
            }
            2 -> {
                context.playSoundWin()
                for (i in winList) {
                    matrix[i.row][i.col].isWinPos.value = true
                }
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isPlayer2Win.value = true
                }, 500)

            }
            3 -> {
                isGameOver.value = true
            }
        }

    }

    @SuppressWarnings("kotlin:S3776")
// cognitive complexity
    fun checkAIWinInNextMove(
        symbols: Array<Array<Symbol>>,
        boardSize: Int,
        player: Int
    ): IntArray? {
        val copyArr: Array<Array<Symbol>> = symbols
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (copyArr[i][j].symbol.value == 0) {
                    copyArr[i][j].symbol.value = player
                    val status = move(symbols, i, j, boardSize, player)
                    // check player 1 win
                    if (player == player1 && status == 1) {
                        return intArrayOf(i, j)
                    }
                    // check player2 win
                    if (player == player2 && status == 2) {
                        return intArrayOf(i, j)
                    }
                    copyArr[i][j].symbol.value = 0
                }
            }
        }

        return null
    }

    @SuppressWarnings("kotlin:S3776")
// cognitive complexity
    private fun move(
        symbols: Array<Array<Symbol>>,
        row: Int,
        col: Int,
        boardSize: Int,
        player: Int
    ): Int {


        winList.clear()

        // horizontal check
        var horizontalPattern = true
        for (i in symbols[row].indices) {
            winList.add(WinPos(row, i))
            if (symbols[row][i].symbol.value != player) {
                winList.clear()
                horizontalPattern = false
                break
            }
        }
        if (horizontalPattern) {
            return if (player == player1) {
                player1_win
            } else {
                player2_win
            }
        }

        // vertical check
        var verticalPattern = true
        for (i in symbols.indices) {
            winList.add(WinPos(i, col))
            if (symbols[i][col].symbol.value != player) {
                winList.clear()
                verticalPattern = false
                break
            }
        }
        if (verticalPattern) {
            return if (player == player1) {
                player1_win
            } else {
                player2_win
            }
        }

        // diagonal check
        var diagonalPattern = true
        var tempRow = 0
        var tempCol = 0
        for (i in symbols.indices) {
            winList.add(WinPos(tempRow, tempCol))
            if (symbols[tempRow][tempCol].symbol.value != player) {
                winList.clear()
                diagonalPattern = false
                break
            }
            tempRow += 1
            tempCol += 1
        }
        if (diagonalPattern) {
            return if (player == player1) {
                player1_win
            } else {
                player2_win
            }
        }

        // diagonal second check
        diagonalPattern = true
        tempRow = 0
        tempCol = boardSize - 1
        for (i in symbols.indices) {
            winList.add(WinPos(tempRow, tempCol))
            if (symbols[tempRow][tempCol].symbol.value != player) {
                winList.clear()
                diagonalPattern = false
                break
            }
            tempRow += 1
            tempCol -= 1
        }
        if (diagonalPattern) {
            return if (player == player1) {
                player1_win
            } else {
                player2_win
            }
        }

        // if no cell is empty
        if (count == boardSize * boardSize) {
            return draw
        }

        return incomplete
    }
}

