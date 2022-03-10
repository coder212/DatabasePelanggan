package com.thisapp.databasepelanggan.model

class ModelDataPelanggan {

    var IdDatabase = 0
    var Name : String?? = null
    var Address : String?? = null
    var PhoneNumber: String?? = null
    var AlamatEmail: String? = null
    var keterangan : String? = null

    constructor(){}

    constructor(Name:String, Address:String, PhoneNumber:String, AlamatEmail:String, Keterangan: String){
       this.Name = Name
       this.Address = Address
       this.PhoneNumber = PhoneNumber
        this.AlamatEmail = AlamatEmail
        this.keterangan = Keterangan
    }

}