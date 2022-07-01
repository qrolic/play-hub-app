package com.qrolic.minesweeper.database

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences (context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences(
            PREFERENCE_NAME,
            0)
    private val editor: SharedPreferences.Editor = preferences.edit()

    fun setBestTime(bestTime:Int){
        editor.putInt("bestTime",bestTime)
        editor.commit()
    }

    fun getBestTime():Int{
        return preferences.getInt("bestTime",0)
    }

    fun setLastTime(lastTime:Int){
        editor.putInt("lastTime",lastTime)
        editor.commit()
    }

    fun getLastTime():Int{
        return preferences.getInt("lastTime",0)
    }


    companion object {
        private const val PREFERENCE_NAME = "mine_sweeper"
    }
}