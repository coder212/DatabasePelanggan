package com.thisapp.databasepelanggan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisapp.databasepelanggan.adapter.DataPelangganAdapter
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityMainBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

class MainActivity : AppCompatActivity(), RecyclerViewClickListener {
    var _binding : ActivityMainBinding? = null
    val binding get() = _binding!!
    var aksi : Aksi? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.text = ""
        aksi = Aksi(binding.root.context)
        aksi!!.open()
        val ada = aksi!!.checkdbexist()
        if(ada){
            val datap = aksi!!.getalldata()
            binding.textView.visibility = View.GONE
            binding.rv.layoutManager = LinearLayoutManager(this)
            val dataPelangganAdapter = DataPelangganAdapter(datap)
            dataPelangganAdapter.listener = this
            binding.rv.adapter = dataPelangganAdapter
            aksi!!.close()
        }else{
            binding.rv.visibility = View.GONE
            binding.text = "database kosong"
            //var modelDataPelanggan =ModelDataPelanggan("Paijo", "jalan terus tanpa mundur", "083111211111222")
            //var arrayList = ArrayList<ModelDataPelanggan>()
            //arrayList.add(modelDataPelanggan)
            //aksi!!.insertData(arrayList)
            aksi!!.close()
        }
        binding.floatingActionButton.setOnClickListener {
            var intent = Intent(binding.root.context, MenambahkanDataActivity::class.java)
            startActivity(intent)
            finish()
        }




    }

    override fun onItemClicked(view: View, dataPelanggan: ModelDataPelanggan) {
        val intent = Intent(binding.root.context, DetailActivity::class.java)
        intent.putExtra("nama", dataPelanggan.Name)
        startActivity(intent)
        finish()
    }
}