package com.qrolic.minesweeper.utils

import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import com.qrolic.minesweeper.R

fun Context.toast(message: String) {

    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.playSoundClick() {
    val mediaPlayer = MediaPlayer.create(this, R.raw.click)
    mediaPlayer.start()
    mediaPlayer.setVolume(2f, 2f)
}

fun Context.playSoundWin() {
    val mediaPlayer = MediaPlayer.create(this, R.raw.win)
    mediaPlayer.start()
    mediaPlayer.setVolume(5f, 5f)
}
