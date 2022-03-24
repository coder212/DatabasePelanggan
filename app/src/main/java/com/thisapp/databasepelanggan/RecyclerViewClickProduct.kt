package com.thisapp.databasepelanggan

import android.view.View
import com.thisapp.databasepelanggan.model.ProductModel

interface RecyclerViewClickProduct {
    fun onItemClick(
        view: View,
        dataProductModel: ProductModel
    )
}