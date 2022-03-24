package com.thisapp.databasepelanggan.model

class CatatanModel {
    var catatanId : Int = 0
    var customerName : String? = null
    var orderDate : String? = null
    var orderTime : String? = null
    var statusPembayaran : String? = null
    var keterangan : String? = null

    constructor(){}
    constructor(namaPelanggan:String, tanggalPesan:String, jamPesan:String, statusPembayaran:String,
    keterangan:String){
        this.customerName = namaPelanggan
        this.orderDate = tanggalPesan
        this.orderTime = jamPesan
        this.statusPembayaran = statusPembayaran
        this.keterangan = keterangan
    }
}