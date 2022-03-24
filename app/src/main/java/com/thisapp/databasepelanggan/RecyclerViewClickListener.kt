package com.thisapp.databasepelanggan

import android.view.View
import com.thisapp.databasepelanggan.model.ModelDataPelanggan
import com.thisapp.databasepelanggan.model.ProductModel

interface RecyclerViewClickListener {
    fun onItemClicked(view: View, dataPelanggan: ModelDataPelanggan)
}