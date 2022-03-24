package com.thisapp.databasepelanggan.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thisapp.databasepelanggan.databinding.LayoutRowBinding
import com.thisapp.databasepelanggan.model.ProductModel
import com.thisapp.databasepelanggan.IndonesianCurrency
import com.thisapp.databasepelanggan.RecyclerViewClickProduct

class adapterProduk(private val produk:ArrayList<ProductModel>):
    RecyclerView.Adapter<adapterProduk.viewHolder>() {
    var listener : RecyclerViewClickProduct? = null
    inner class viewHolder(val binding: LayoutRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = LayoutRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
       with(holder){
           with(produk[position]) {
               binding.nama =this.productName
               val idrformat = IndonesianCurrency()
               binding.harga = idrformat.NumberFormatRupiah(this.productPrice)
           }
       }
        holder.binding.tvName.setOnClickListener {
            listener?.onItemClick(it, produk[position])
        }
    }

    override fun getItemCount(): Int {
       return produk.size
    }
}