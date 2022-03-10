package com.thisapp.databasepelanggan

import android.view.View
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, dataPelanggan: ModelDataPelanggan)
}