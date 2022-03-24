package com.thisapp.databasepelanggan.ui.kalkulator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KalkulatorViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply{
        value = "Kalkulator"
    }
    val text : LiveData<String> = _text
}