package com.qrolic.minesweeper.activity.splash

sealed class Screen(val route:String){
    object SplashScreen:Screen("splash")
    object MainScreen:Screen("main")

    // TicTacToe Screens
    object SelectGameType:Screen("TicTacToe")
    object SelectBoardSize:Screen("Board Size")
    object TicTacToe:Screen("TicTacToe Board")


    object Othello:Screen("Othello Board")
    object OthelloSelectGameType:Screen("Othello")

}