package com.qrolic.minesweeper.model

import androidx.compose.ui.graphics.Color

data class GameModel(
    val tag:String,
    val title:String,
    val icon:Int,
    val background:Color
)