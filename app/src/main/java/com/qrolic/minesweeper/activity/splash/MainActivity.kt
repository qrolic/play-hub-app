package com.qrolic.minesweeper.activity.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.ui.theme.MineSweeperTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var mediaPlayer: MediaPlayer
    private var length:Int = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MineSweeperTheme {
                MainForm()
            }
        }

        //Play music
        mediaPlayer = MediaPlayer.create(this, R.raw.background)
        lifecycleScope.launch {
            delay(3500)
            mediaPlayer.start()
        }
        mediaPlayer.setVolume(0.2f,0.2f)
        // repeat
        mediaPlayer.setOnCompletionListener {
            it.start()
        }
    }


    override fun onResume() {
        super.onResume()
        if (isPaused){
            mediaPlayer.seekTo(length)
            mediaPlayer.start()
        }
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
        length = mediaPlayer.currentPosition
        isPaused = true
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
    }


    @Composable
    fun MainForm() {
        // Navigation
        val navController = rememberNavController()
        NavController(navController = navController,this)
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MineSweeperTheme {
            MainForm()
        }
    }

    @Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
    @Composable
    fun DefaultPreviewDarkMode() {
        MineSweeperTheme {
            MainForm()
        }
    }
}

