package com.qrolic.minesweeper.activity

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.qrolic.minesweeper.R
import com.qrolic.minesweeper.databinding.ActivityMineSweeperBinding

class MineSweeperActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMineSweeperBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var length:Int = 0
    private var isPaused = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineSweeperBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Play music
        mediaPlayer = MediaPlayer.create(this, R.raw.background)
        mediaPlayer.start()
        mediaPlayer.setVolume(0.2f, 0.2f)
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


}