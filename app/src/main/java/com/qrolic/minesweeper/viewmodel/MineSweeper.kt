package com.qrolic.minesweeper.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MineSweeper : ViewModel() {

        private var _count = MutableLiveData(0)
        val count: LiveData<Int>
            get() = _count

        var isFirstClick = true
        var choice = MutableLiveData(1)
        var flaggedMines = MutableLiveData(0)


    /*
    * increase time counter value
    * */
    fun increaseCount() {
        _count.value?.let {
            _count.value = it + 1
        }
    }

}