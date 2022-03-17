package com.thisapp.databasepelanggan

import android.content.ContentResolver
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
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
            val ctact = getcontacts()
            //Snackbar.make( binding.root, "Nama: "+ctact[100].Name+" notelp: "+ctact[100].PhoneNumber+" mail: "+ctact[100].AlamatEmail+" photo: "+ctact[100].pProfile, Snackbar.LENGTH_LONG).show()
            dialogwindowcontact(ctact)

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

    private fun dialogwindowcontact(ctact: ArrayList<ModelDataPelanggan>) {
        dialogBuilder = AlertDialog.Builder(binding.root.context)
        tutup = dialogBuilder.create()
        dialogBuilder.setTitle("Kontak")
        bindingDialog = CustomdialogBinding.inflate(layoutInflater)
        tutup.setView(bindingDialog.root)
        if ((ctact != null) && ctact.isNotEmpty()) {
            bindingDialog.textView.visibility = View.GONE
            bindingDialog.rv.layoutManager = LinearLayoutManager(this)
            val dataPelangganAdapter = DataPelangganAdapter(ctact)
            dataPelangganAdapter.listener = this
            bindingDialog.rv.adapter = dataPelangganAdapter
        } else {
            bindingDialog.rv.visibility = View.GONE
            bindingDialog.text = "Kamu tidak memiliki kontak"
        }
        tutup.setTitle("Kontak")
        tutup.setCancelable(true)
        tutup.show()
    }

    private fun getcontacts(): ArrayList<ModelDataPelanggan> {
        var result = ArrayList<ModelDataPelanggan>()
        val resolver: ContentResolver = contentResolver
        val cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null)
        if (cursor!!.count > 0) {
            while (cursor.moveToNext()) {
                val modelDataPelanggan = ModelDataPelanggan()
                val idcontact =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
                modelDataPelanggan.Name =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                val notelp =
                    cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                        .toInt()
                if (notelp > 0) {
                    val cursorPhone = contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
                        arrayOf(idcontact), null
                    )
                    if (cursorPhone!!.count > 0) {
                        while (cursorPhone!!.moveToNext()) {
                            modelDataPelanggan.PhoneNumber = cursorPhone.getString(
                                cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                            )
                        }

                    }
                    cursorPhone.close()
                }
                val emailcursor = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID+"=?", arrayOf(idcontact), null)
                if (emailcursor!!.count>0){
                    while (emailcursor!!.moveToNext()){
                        modelDataPelanggan.AlamatEmail= emailcursor.getString(emailcursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Email.ADDRESS))
                    }
                }
                emailcursor.close()
                modelDataPelanggan.pProfile = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI))
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