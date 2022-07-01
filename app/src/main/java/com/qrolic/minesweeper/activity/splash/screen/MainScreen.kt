@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.qrolic.minesweeper.activity.splash.screen

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.activity.MineSweeperActivity
import com.qrolic.minesweeper.model.GameModel
import com.qrolic.minesweeper.ui.theme.NotoSans
import com.qrolic.minesweeper.utils.playSoundClick


@Composable
fun MainScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = NotoSans,
                        color = MaterialTheme.colorScheme.onSecondary,
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    ) {
        GameList(navController)
    }
}

@Composable
fun GameList(navController: NavController) {
    val gameList = listOf(
        GameModel("TicTacToe","Tic Tac Toe", R.drawable.tic_tac_toe, MaterialTheme.colorScheme.primary),
        GameModel("Othello","Othello", R.drawable.othello, MaterialTheme.colorScheme.tertiary),
        GameModel("MineSweeper","Mine Sweeper", R.drawable.mine_sweeper, MaterialTheme.colorScheme.primary)
    )
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary)
            .padding(top=15.dp)
    ) {
        items(
            items = gameList,
            itemContent = { game: GameModel ->
                GameCard(game, navController)
            }
        )
    }
}

@Composable
fun GameCard(game: GameModel, navController: NavController) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(20))
            .background(game.background)
            .clickable {
                context.playSoundClick()
                if(game.tag == "MineSweeper"){
                    val intent = Intent(context,MineSweeperActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
                }else{
                    navController.navigate(game.tag)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = game.icon),
                contentDescription = game.title,
                modifier = Modifier
                    .size(120.dp)
                    .padding(12.dp)
                    .align(Alignment.CenterVertically),
            )
            Text(
                text = game.title.uppercase(), fontFamily = NotoSans,
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

    }


}

@Preview(showBackground = true)
@Composable
fun PreviewOthello() {
    val navController = NavController(context = LocalContext.current)
    MainScreen(navController = navController)
}