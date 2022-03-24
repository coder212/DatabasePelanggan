package com.thisapp.databasepelanggan.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GalleryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Data Produk belum Terisi"
    }
    val text: LiveData<String> = _text
}