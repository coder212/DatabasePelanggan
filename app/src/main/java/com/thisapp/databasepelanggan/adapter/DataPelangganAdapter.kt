package com.thisapp.databasepelanggan.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ImageDecoder
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getColor
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import com.thisapp.databasepelanggan.R
import com.thisapp.databasepelanggan.RecyclerViewClickListener
import com.thisapp.databasepelanggan.databinding.RowlayoutBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan
import kotlin.random.Random

class DataPelangganAdapter( private val dList: List<ModelDataPelanggan>): RecyclerView.Adapter<DataPelangganAdapter.ViewHolder>() {
    lateinit var ctx : Context
    var listener : RecyclerViewClickListener? = null
    inner class ViewHolder(val binding: RowlayoutBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataPelangganAdapter.ViewHolder {
       val binding = RowlayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        ctx= binding.root.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataPelangganAdapter.ViewHolder, position: Int) {
       // TODO("Not yet implemented")
        with(holder){
            with(dList[position]){
                binding.nama = this.Name
                if(this.pProfile == null){
                    var inisial = if(this.Name!!.length<2){
                        this.Name!![0].uppercase()
                    }else{
                        ""+ this.Name!![0].uppercaseChar() +this.Name!![1].uppercaseChar()
                    }
                    binding.gambar = BitmapDrawable( ctx.resources,  drawTextImage(inisial))
                }else{
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoder.decodeBitmap(ImageDecoder.createSource(ctx.contentResolver, Uri.parse(this.pProfile!!)))
                    } else {
                        MediaStore.Images.Media.getBitmap(ctx.contentResolver, Uri.parse(this.pProfile!!))
                    }
                    binding.gambar = BitmapDrawable(ctx.resources, bitmap)

                }

                if(this.PhoneNumber!=null){
                    binding.notelp = this.PhoneNumber
                }else{
                    binding.notelpon.visibility = View.GONE
                }
                if(this.AlamatEmail!=null){
                    binding.mail = this.AlamatEmail
                }else{
                    binding.email.visibility = View.GONE
                }

            }
        }
        holder.binding.textView2.setOnClickListener {
            listener?.onItemClicked(it, dList[position])
        }
    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")
        return dList.size
    }

    private fun drawTextImage(inisial:String) : Bitmap{
        val bitmapConfig = Bitmap.Config.ARGB_8888
        val bitmap = Bitmap.createBitmap(100, 100, bitmapConfig)
        bitmap.scale(100,100, false)
        val canvas = Canvas(bitmap)
        val ptext = Paint(Paint.ANTI_ALIAS_FLAG)
        val warna = ctx.resources.getIntArray(R.array.cschema)
        val awarna = warna.random()
        canvas.drawColor(awarna)
        ptext.color = getColor(ctx, R.color.white)
        ptext.textSize = 50f
        canvas.drawText(inisial, 25f,80f, ptext)
        return bitmap

    }


}
