package com.thisapp.databasepelanggan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    var _binding: ActivityDetailBinding? = null
    val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var name = ""
        var idDb =0
        name = intent.getStringExtra("nama").toString()
        Log.d("logging", name)
        if(name == ""){
        }else{
            supportActionBar?.setDisplayShowTitleEnabled(true)
            supportActionBar?.title = name
            var aksi = Aksi(binding.root.context)
            aksi.open()
            var datadetail = aksi.getdetailbyname(name)
            binding.nama = datadetail.Name
            binding.notelpn = datadetail.PhoneNumber
            binding.alamat = datadetail.Address
            binding.mail = datadetail.AlamatEmail
            binding.keterangan = datadetail.keterangan
            idDb = datadetail.IdDatabase
            if(datadetail.PhoneNumber == null || datadetail.PhoneNumber == "") {
                binding.textView10.visibility = View.GONE
                binding.phne.visibility = View.GONE
            }
            if(datadetail.Address== null || datadetail.Address == "") {
                binding.textView12.visibility = View.GONE
                binding.location.visibility = View.GONE
            }
            if(datadetail.AlamatEmail == null || datadetail.AlamatEmail == "") {
                binding.textView7.visibility = View.GONE
                binding.imageView5.visibility = View.GONE
            }
            if(datadetail.keterangan == null || datadetail.keterangan == ""){
                binding.textView9.visibility = View.GONE
                binding.imageView6.visibility = View.GONE
            }
            aksi.close()
        }
        binding.hapusbutton.setOnClickListener {
            var aksi = Aksi(binding.root.context)
            aksi.open()
            aksi.deleteData(idDb)
            aksi.close()
            val intent = Intent(binding.root.context, aksiActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.ubahbutton.setOnClickListener {
            val intent = Intent(binding.root.context, EditActivity::class.java)
            intent.putExtra("nama", binding.nama)
            startActivity(intent)
            finish()
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(binding.root.context, aksiActivity::class.java)
        startActivity(intent)
        finish()
    }
}