package com.thisapp.databasepelanggan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityTambahProdukBinding
import com.thisapp.databasepelanggan.model.ProductModel

class TambahProdukActivity : AppCompatActivity() {

    lateinit var binding: ActivityTambahProdukBinding
    lateinit var aksi :Aksi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahProdukBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.namaproduk.hint = "Masukkan Nama Produk"
        binding.editTextPrice.hint = "Masukkan Harga Produk"
        binding.textView3.visibility = View.GONE
        aksi = Aksi(this)
        binding.imageButton3.setOnClickListener {
            var namaproduk = binding.namaproduk.text.toString()
            var hargaProduk = if(binding.editTextPrice.text.toString()!=""){
                binding.editTextPrice.text.toString().toDouble()
            }else{
                0.0
            }
            if((namaproduk.trim().length>0) && (hargaProduk>0.0) ){
                aksi.open()
                var modelDataProduk = ProductModel(namaproduk, hargaProduk)
                var arrayList = ArrayList<ProductModel>()
                arrayList.add(modelDataProduk)
                aksi.insertProduct(arrayList)
                aksi.close()
                var intent = Intent(binding.root.context, aksiActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                binding.textView3.visibility = View.VISIBLE
                binding.textView3.text = "Nama Produk dan Harganya harus Diisi"
            }
        }

    }
}