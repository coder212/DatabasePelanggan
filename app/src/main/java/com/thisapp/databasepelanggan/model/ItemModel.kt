package com.thisapp.databasepelanggan.model

class ItemModel {
    var itemId : Int = 0
    var productName : String? = null
    var itemQuantity: Int = 0
    var customerName : String? = null

    constructor(){}
    constructor(namaProduk:String, itemQuantity:Int, namaPelangan:String){
        this.productName = namaProduk
        this.itemQuantity = itemQuantity
        this.customerName = namaPelangan
    }
}