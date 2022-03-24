package com.thisapp.databasepelanggan.model

class calculatorModel {
    var productName: String? = null
    var productPrice: Double = 0.0
    var productSubtotal: Double = 0.0
    var productTotal : Double = 0.0

    constructor(){}
    constructor(productName:String, productPrice:Double, productSubTotal:Double, productTotal:Double){
        this.productName = productName
        this.productPrice = productPrice
        this.productSubtotal = productSubtotal
        this.productTotal = productTotal
    }
}