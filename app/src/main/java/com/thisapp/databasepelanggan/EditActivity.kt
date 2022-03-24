package com.thisapp.databasepelanggan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityEditBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

class EditActivity : AppCompatActivity() {
    var _binding : ActivityEditBinding? = null
    val binding get() = _binding!!
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val nama = intent.getStringExtra("nama")
        var aksi = Aksi(binding.root.context)
        aksi.open()
        var datadetail = aksi.getdetailbyname(nama!!)
        binding.editTextTextPersonName.setText(""+nama)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.title = nama
        if(datadetail.PhoneNumber != null) {
            binding.editTextPhone.setText(datadetail.PhoneNumber)
        }
        if(datadetail.Address != null) {
            binding.editTextTextMultiLine.setText(datadetail.Address)
        }
        if(datadetail.AlamatEmail != null){
            binding.editTextTextEmailAddress.setText(datadetail.AlamatEmail)
        }
        if(datadetail.keterangan != null){
            binding.editTextTextMultiLine2.setText(datadetail.keterangan)
        }
        aksi.close()
        binding.update.setOnClickListener {
            if(datadetail.IdDatabase>0){
                val nama = binding.editTextTextPersonName.text.toString()
                val notelp = binding.editTextPhone.text.toString()
                val alamat = binding.editTextTextMultiLine.text.toString()
                val email = binding.editTextTextEmailAddress.text.toString()
                val keterangan = binding.editTextTextMultiLine2.text.toString()
                val aksi = Aksi(binding.root.context)
                aksi.open()
                val modelDataPelanggan = ModelDataPelanggan()
                modelDataPelanggan.IdDatabase= datadetail.IdDatabase
                modelDataPelanggan.Name = nama
                modelDataPelanggan.PhoneNumber = notelp
                modelDataPelanggan.Address = alamat
                modelDataPelanggan.AlamatEmail = email
                modelDataPelanggan.keterangan = keterangan
                aksi.updateData(modelDataPelanggan)
                aksi.close()
                val intent = Intent(binding.root.context, aksiActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        binding.batal.setOnClickListener {
            val intent = Intent(binding.root.context, aksiActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        //
        val intent = Intent(binding.root.context, aksiActivity::class.java)
        startActivity(intent)
        finish()
    }
}