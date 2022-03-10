package com.thisapp.databasepelanggan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityMenambahkanDataBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

class MenambahkanDataActivity : AppCompatActivity() {
    var _binding : ActivityMenambahkanDataBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMenambahkanDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = "New Client"
        binding.imageButton.setOnClickListener {
            var nama = binding.editTextTextPersonName.text.toString()
            val nomorTelpon = binding.editTextPhone.text.toString()
            var alamat = binding.editTextTextMultiLine.text.toString()
            var email = binding.editTextTextEmailAddress.text.toString()
            var keterangan = binding.editTextTextMultiLine2.text.toString()
            if(nama.trim().length>0){
                var aksi = Aksi(binding.root.context)
                aksi.open()
                var modelDataPelanggan = ModelDataPelanggan(nama, alamat, nomorTelpon, email, keterangan)
                var arrayList = ArrayList<ModelDataPelanggan>()
                arrayList.add(modelDataPelanggan)
                aksi.insertData(arrayList)
                aksi.close()
                var intent = Intent(binding.root.context, MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                binding.textView6.visibility = View.VISIBLE
                binding.pesanerror = "Data Harus Diisi"
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}