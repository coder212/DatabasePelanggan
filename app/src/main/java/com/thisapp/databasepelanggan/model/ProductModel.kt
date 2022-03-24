package com.thisapp.databasepelanggan.model

class ProductModel {
    var productId:Int = 0
    var productName : String? = null
    var productPrice : Double = 0.0

    constructor(){}
    constructor(namaProduk:String, hargaProduk:Double){
        this.productName = namaProduk
        this.productPrice = hargaProduk
    }

}