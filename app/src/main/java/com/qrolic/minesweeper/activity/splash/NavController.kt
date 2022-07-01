package com.qrolic.minesweeper.activity.splash

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.qrolic.minesweeper.activity.splash.screen.AnimatedSplashScreen
import com.qrolic.minesweeper.activity.splash.screen.MainScreen
import com.qrolic.minesweeper.activity.splash.screen.othello.Othello
import com.qrolic.minesweeper.activity.splash.screen.othello.OthelloSelectGameType
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.SelectBoardSize
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.SelectGameType
import com.qrolic.minesweeper.activity.splash.screen.tictactoe.TicTacToe

@Composable
fun NavController(navController: NavHostController, activity: Activity) {

    val x = 7
    val y: Long = x.toLong()
    println(y)

    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(
            Screen.SplashScreen.route,
        ) {
            AnimatedSplashScreen(navController = navController)
        }
        composable(Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }

        // TicTacToe
        composable(Screen.SelectGameType.route) {
            SelectGameType(navController = navController)
        }
        composable(Screen.SelectBoardSize.route + "/{id}") { navBackStack ->
            val type = navBackStack.arguments?.getString("id")
            SelectBoardSize(navController = navController, type ?: "AI", activity)
        }
        composable(
            Screen.TicTacToe.route + "/{type}/{boardSize}",
            arguments = listOf(
                navArgument("type") { type = NavType.StringType },
                navArgument("boardSize") { type = NavType.StringType },
            )
        ) { navBackStack ->
            val type = navBackStack.arguments?.getString("type")
            val boardSize = navBackStack.arguments?.getString("boardSize")
            TicTacToe(navController = navController, boardSize ?: "3", type ?: "AI", activity)
        }


        // othello
        composable(Screen.OthelloSelectGameType.route) {
            OthelloSelectGameType(navController = navController)
        }
        composable(Screen.Othello.route + "/{id}") { navBackStack ->
            navBackStack.arguments?.let {
                val type = navBackStack.arguments!!.getString("id")
                Othello(navController = navController, type!!, activity)
            }
        }


    }

}

