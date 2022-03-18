package com.thisapp.databasepelanggan

import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import android.location.Location.convert
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.thisapp.databasepelanggan.adapter.DataPelangganAdapter
import com.thisapp.databasepelanggan.database.Aksi
import com.thisapp.databasepelanggan.databinding.ActivityMenambahkanDataBinding
import com.thisapp.databasepelanggan.databinding.CustomdialogBinding
import com.thisapp.databasepelanggan.model.ModelDataPelanggan

class MenambahkanDataActivity : AppCompatActivity(), RecyclerViewClickListener{
    var _binding: ActivityMenambahkanDataBinding? = null
    val binding get() = _binding!!
    lateinit var dialogBuilder : AlertDialog.Builder
    lateinit var tutup : AlertDialog
    lateinit var bindingDialog : CustomdialogBinding
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
            if (nama.trim().length > 0) {
                var aksi = Aksi(binding.root.context)
                aksi.open()
                var modelDataPelanggan =
                    ModelDataPelanggan(nama, alamat, nomorTelpon, email, keterangan)
                var arrayList = ArrayList<ModelDataPelanggan>()
                arrayList.add(modelDataPelanggan)
                aksi.insertData(arrayList)
                aksi.close()
                var intent = Intent(binding.root.context, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.textView6.visibility = View.VISIBLE
                binding.pesanerror = "Data Harus Diisi"
            }
        }
        binding.floatingActionButton2.setOnClickListener {
            dialogwindowcontact()

        }

        val getcontactPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { grantedPermission ->
            if (grantedPermission) {

            } else {
                Snackbar.make(
                    binding.root,
                    "harus dapat izin read kontak",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        getcontactPermission.launch(android.Manifest.permission.READ_CONTACTS)

    }

    private fun dialogwindowcontact() {
        dialogBuilder = AlertDialog.Builder(binding.root.context)
        tutup = dialogBuilder.create()
        bindingDialog = CustomdialogBinding.inflate(layoutInflater)
        tutup.setView(bindingDialog.root)
        bindingDialog.textView.visibility = View.INVISIBLE
        bindingDialog.rv.visibility = View.INVISIBLE
        tutup.setTitle("Kontak")
        tutup.setCancelable(true)
        tutup.show()
        bindingDialog.idSearch.queryHint = "Masukan nama yang akan dicari..."
        bindingDialog.idSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val ctact = getContactByName(query!!)
                searchproses(ctact)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=""){
                    val ctact = getContactByName(newText!!)
                    searchproses(ctact)
                }else{
                   retrieveAndShowData()
                }
                return false
            }

        })
        bindingDialog.idSearch.setOnCloseListener {
            retrieveAndShowData()
            false
        }
        retrieveAndShowData()

    }

    private fun searchproses(ctact: ArrayList<ModelDataPelanggan>) {
        if ((ctact != null) && ctact.isNotEmpty()) {
            bindingDialog.rv.visibility = View.VISIBLE
            bindingDialog.textView.visibility = View.INVISIBLE
            bindingDialog.progressBar.visibility = View.INVISIBLE
            bindingDialog.rv.layoutManager = LinearLayoutManager(this)
            val dataPelangganAdapter = DataPelangganAdapter(ctact)
            dataPelangganAdapter.listener = this
            bindingDialog.rv.adapter = dataPelangganAdapter
        } else {
            bindingDialog.rv.visibility = View.GONE
            bindingDialog.progressBar.visibility = View.INVISIBLE
            bindingDialog.textView.visibility = View.VISIBLE
            bindingDialog.text = "Nama yang Kamu cari tidak terdapat dalam Kontak"
        }
    }

    private fun retrieveAndShowData() {
        lateinit var ctact: ArrayList<ModelDataPelanggan> // = "" //getcontacts()
        ctact = getcontacts()
        if ((ctact != null) && ctact.isNotEmpty()) {
            bindingDialog.rv.visibility = View.VISIBLE
            bindingDialog.textView.visibility = View.INVISIBLE
            bindingDialog.progressBar.visibility = View.INVISIBLE
            bindingDialog.rv.layoutManager = LinearLayoutManager(this)
            val dataPelangganAdapter = DataPelangganAdapter(ctact)
            dataPelangganAdapter.listener = this
            bindingDialog.rv.adapter = dataPelangganAdapter
        } else {
            bindingDialog.progressBar.visibility = View.INVISIBLE
            bindingDialog.textView.visibility = View.VISIBLE
            bindingDialog.text = "Kamu tidak memiliki kontak"
        }
    }

    private fun getcontacts(): ArrayList<ModelDataPelanggan> {
        var result = ArrayList<ModelDataPelanggan>()
        val selectionFields =  ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?"
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI), selectionFields, arrayOf("com.google"), ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC")
        if (cursor!!.count > 0) {
            val indexId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val indexNama =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val indexNum =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val indexPProfile =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)

            while (cursor.moveToNext()){
                val modelDataPelanggan = ModelDataPelanggan()
                modelDataPelanggan.IdDatabase = cursor.getString(indexId).toInt()
                modelDataPelanggan.PhoneNumber = cursor.getString(indexNum)
                modelDataPelanggan.Name = cursor.getString(indexNama)
                modelDataPelanggan.pProfile = cursor.getString(indexPProfile)
                result.add(modelDataPelanggan)

            }


        }
        cursor.close()
        return result
    }

    private fun getContactByName(name:String): ArrayList<ModelDataPelanggan>{
        var result = ArrayList<ModelDataPelanggan>()
        val selectionFields =  ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" LIKE ?  AND "+ContactsContract.RawContacts.ACCOUNT_TYPE + " = ?"
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, arrayOf(
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI), selectionFields, arrayOf( "%"+name+"%","com.google"), ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME +" ASC")
        Log.d("log", ""+cursor!!.count)
        if (cursor!!.count > 0) {
            val indexId = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val indexNama =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val indexNum =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val indexPProfile =  cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)

            while (cursor.moveToNext()){
                val modelDataPelanggan = ModelDataPelanggan()
                modelDataPelanggan.IdDatabase = cursor.getString(indexId).toInt()
                modelDataPelanggan.PhoneNumber = cursor.getString(indexNum)
                modelDataPelanggan.Name = cursor.getString(indexNama)
                modelDataPelanggan.pProfile = cursor.getString(indexPProfile)
                result.add(modelDataPelanggan)

            }


        }
        cursor.close()
        return result
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(binding.root.context, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onItemClicked(view: View, dataPelanggan: ModelDataPelanggan) {
        binding.editTextTextPersonName.setText(dataPelanggan.Name)
        if(dataPelanggan.PhoneNumber!=null){
            binding.editTextPhone.setText(dataPelanggan.PhoneNumber)
        }

        if(dataPelanggan.AlamatEmail!= null){
            binding.editTextTextEmailAddress.setText(dataPelanggan.AlamatEmail)
        }
        tutup.dismiss()
    }

}