package com.qrolic.minesweeper.activity.splash.screen.othello

import android.content.Context
import android.os.Looper
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.utils.playSoundWin

class OthelloBoard {

    companion object {
        private val xDir = intArrayOf(-1, -1, 0, 1, 1, 1, 0, -1)
        private val yDir = intArrayOf(0, 1, 1, 1, 0, -1, -1, -1)
        const val player1Symbol = R.drawable.white_circle
        const val player2Symbol = R.drawable.black_circle
        var player = player1Symbol
        private const val player1_win = 1
        private const val player2_win = 2
        private const val draw = 3
        private const val invalid = 4
        var isPlayer1Win = mutableStateOf(false)
        var isPlayer2Win = mutableStateOf(false)
        var isGameOver = mutableStateOf(false)
        var count = 0

    }



    fun getRandomRowCol(board: Array<Array<MutableState<Int>>>,symbol: Int): IntArray? {
        val tempList = arrayListOf<String>()
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                // board cell should be empty and move should be valid
                if (board[i][j].value == 0 && isMovePossible(i,j,board,symbol)) {
                    tempList.add("$i$j")
                }
            }
        }

        if (tempList.isEmpty()){
            return  null
        }
        val temp = tempList.random()

        return intArrayOf(temp.substring(0, 1).toInt(), temp.substring(1).toInt())
    }


    fun stateNavigation(state: Int, context: Context){
        when (state) {
            1 -> {
                context.playSoundWin()
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isPlayer1Win.value = true
                }, 2000)
            }
            2 -> {
                context.playSoundWin()

                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isPlayer2Win.value = true
                }, 2000)
            }
            3 -> {

                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    isGameOver.value = true
                }, 2000)

            }
            else -> {
                // incomplete
            }
        }
    }



    fun state(board: Array<Array<MutableState<Int>>>,symbol: Int): Int {
        // is game incomplete
        // check for one by one position
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                if (board[i][j].value == 0 && isMovePossible(i,j,board, symbol)) {
                    return invalid
                }
            }
        }


        // if it's not complete
        // check which player has max symbols
        return checkWinnerState(board)

    }

    fun checkWinnerState(board: Array<Array<MutableState<Int>>>):Int{
        // if it's not complete
        // check which player has max symbols
        var whiteSymCount = 0
        var blackSymCount = 0
        var noSymCount = 0
        for (i in 0 until boardSize) {
            for (j in 0 until boardSize) {
                when (board[i][j].value) {
                    player1Symbol -> {
                        whiteSymCount++
                    }
                    player2Symbol -> {
                        blackSymCount++
                    }
                    else -> {
                        noSymCount++
                    }
                }
            }
        }

        return if (whiteSymCount == blackSymCount) {
            draw
        } else if (whiteSymCount > blackSymCount) {
            player1_win
        } else {
            player2_win
        }
    }

    private fun isMovePossible(x: Int, y: Int, board: Array<Array<MutableState<Int>>>,symbol: Int): Boolean {
        // check for valid index of matrix
        if (x < 0 || y < 0 || x >= 8 || y >= 8 || board[x][y].value != 0) {
            return false
        }

        // get opposite symbol
        val tempSymbol = if (symbol == player1Symbol) {
            player2Symbol
        } else {
            player1Symbol
        }

        var isMoved = false

        // check in all  directions if valid pattern matches
        for (i in 0..7) {
            var k = x + xDir[i]
            var l = y + yDir[i]

            // check for opposite symbols
            while (isValidIdx(k, l) && board[k][l].value == tempSymbol) {
                k += xDir[i]
                l += yDir[i]
            }

            // check for last one is current player symbol
            if (isValidIdx(k, l) && board[k][l].value == symbol) {
                val temp1 = x + xDir[i]
                val temp2 = y + yDir[i]

                // set current player symbol to opposite one
                if (temp1 != k || temp2 != l) {
                    isMoved = true
                }
            }


        }

        return isMoved
    }

    @SuppressWarnings("kotlin:S3776")
// cognitive complexity
    fun move(x: Int, y: Int, board: Array<Array<MutableState<Int>>>,symbol:Int): Boolean {

        // check for valid index of matrix
        if (x < 0 || y < 0 || x >= 8 || y >= 8 || board[x][y].value != 0) {
            return false
        }

        // get opposite symbol
        val tempSymbol = if (symbol == player1Symbol) {
            player2Symbol
        } else {
            player1Symbol
        }

        var isMoved = false

        // check in all  directions if valid pattern matches
        for (i in 0..7) {
            var k = x + xDir[i]
            var l = y + yDir[i]

            // check for opposite symbols
            while (isValidIdx(k, l) && board[k][l].value == tempSymbol) {
                k += xDir[i]
                l += yDir[i]
            }

            // check for last one is current player symbol
            if (isValidIdx(k, l) && board[k][l].value == symbol) {
                var temp1 = x + xDir[i]
                var temp2 = y + yDir[i]

                // set current player symbol to opposite one
                while (temp1 != k || temp2 != l) {
                    board[temp1][temp2].value = symbol
                    isMoved = true
                    temp1 += xDir[i]
                    temp2 += yDir[i]
                }
            }

            // finally set symbol at user clicked position
            if (isMoved) {
                board[x][y].value = symbol
            }

        }

        return isMoved
    }

    private fun isValidIdx(x: Int, y: Int): Boolean {
        return !(x < 0 || y < 0 || x >= 8 || y >= 8)
    }
}