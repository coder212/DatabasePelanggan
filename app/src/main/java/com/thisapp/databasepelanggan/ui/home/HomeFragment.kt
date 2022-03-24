package com.thisapp.databasepelanggan.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisapp.databasepelanggan.DetailActivity
import com.thisapp.databasepelanggan.MenambahkanDataActivity
import com.thisapp.databasepelanggan.R
import com.thisapp.databasepelanggan.RecyclerViewClickListener
import com.thisapp.databasepelanggan.adapter.DataPelangganAdapter
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.FragmentHomeBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

class HomeFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    var aksi : Aksi? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.textView.visibility = View.GONE
        aksi = Aksi(binding.root.context)
        aksi!!.open()
        val ada = aksi!!.checkdbexist()
        if(ada){
            val datap = aksi!!.getalldata()
            binding.rv.layoutManager = LinearLayoutManager(binding.root.context)
            val dataPelangganAdapter = DataPelangganAdapter(datap)
            dataPelangganAdapter.listener = this
            binding.rv.adapter = dataPelangganAdapter
            aksi!!.close()
        }else{
            binding.rv.visibility = View.GONE
           binding.textView.visibility = View.VISIBLE
            //binding.text = "database kosong"
            //var modelDataPelanggan =ModelDataPelanggan("Paijo", "jalan terus tanpa mundur", "083111211111222")
            //var arrayList = ArrayList<ModelDataPelanggan>()
            //arrayList.add(modelDataPelanggan)
            //aksi!!.insertData(arrayList)
            aksi!!.close()
        }
        binding.floatingActionButton.setOnClickListener{
            var intent = Intent(binding.root.context, MenambahkanDataActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(view: View, dataPelanggan: ModelDataPelanggan) {
        val intent = Intent(binding.root.context, DetailActivity::class.java)
        intent.putExtra("nama", dataPelanggan.Name)
        startActivity(intent)
        activity?.finish()
    }
}