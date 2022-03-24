package com.thisapp.databasepelanggan.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.thisapp.databasepelanggan.*
import com.thisapp.databasepelanggan.adapter.DataPelangganAdapter
import com.thisapp.databasepelanggan.adapter.adapterProduk
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.FragmentGalleryBinding
import com.thisapp.databasepelanggan.model.ProductModel

class GalleryFragment : Fragment(), RecyclerViewClickProduct {

    private lateinit var galleryViewModel: GalleryViewModel
    private var _binding: FragmentGalleryBinding? = null
    var aksi: Aksi? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.textView.visibility = View.GONE
        aksi = Aksi(binding.root.context)
        aksi!!.open()
        val ada = aksi!!.checktableproductexist()
        if(ada){
            val datap = aksi!!.getAllProduct()
            binding.rv.layoutManager = LinearLayoutManager(binding.root.context)
            val produkAdapter = adapterProduk(datap)
            produkAdapter.listener = this
            binding.rv.adapter = produkAdapter
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
            var intent = Intent(binding.root.context, TambahProdukActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.text = it
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(view: View, dataProductModel: ProductModel) {
        val intent = Intent(binding.root.context, DetailProdukActivity::class.java)
        intent.putExtra("produkName", dataProductModel.productName)
        startActivity(intent)
        activity?.finish()
    }
}