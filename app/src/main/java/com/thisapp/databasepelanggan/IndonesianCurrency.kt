package com.thisapp.databasepelanggan

import java.text.NumberFormat
import java.util.*

class IndonesianCurrency {

    constructor(){}

   fun NumberFormatRupiah(duit: Double): String{
        val id = Locale("in", "ID")
        val formater = NumberFormat.getCurrencyInstance(id)
        return formater.format(duit)
    }
}