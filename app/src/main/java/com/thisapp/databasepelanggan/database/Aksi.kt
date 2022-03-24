package com.thisapp.databasepelanggan.database

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.ContactsContract
import android.util.Log
import com.thisapp.databasepelanggan.model.ModelDataPelanggan
import com.thisapp.databasepelanggan.model.ProductModel
import java.sql.SQLException
import kotlin.jvm.Throws

class Aksi(private var context: Context) {
    companion object{
        private var TABLE_NAME = Config.TABLE_NAME
        private var COLUMN_ID_DATABASE = Config.COLUMN_ID_DATABASE
        private var COLUMN_NAME = Config.COLUMN_NAME
        private var COLUMN_ADDRESS = Config.COLUMN_ADDRESS
        private var COLUMN_PHONE_NUMBER = Config.COLUMN_PHONE_NUMBER
        private var COLUMN_EMAIL_ADDRESS = Config.COLUMN_EMAIL_ADDRESS
        private var COLUMN_KETERANGAN = Config.COLUMN_KETERANGAN

    }

    private var helper: Helper? = null
    private var sqlitedatabase: SQLiteDatabase? = null

    @Throws(SQLException::class)
    fun open(): Aksi {
        helper = Helper(context)
        sqlitedatabase = helper!!.writableDatabase
        return this
    }

    fun close(){
        helper!!.close()
    }

    fun insertData(arrayList: ArrayList<ModelDataPelanggan>){
        val sql = "INSERT INTO "+ TABLE_NAME+" ("+
                COLUMN_NAME+", "+ COLUMN_ADDRESS+", "+ COLUMN_PHONE_NUMBER+", "+ COLUMN_EMAIL_ADDRESS+","+ COLUMN_KETERANGAN+
                ") VALUES(?, ?, ?, ?, ?)"
        sqlitedatabase!!.beginTransaction()
        val stmt = sqlitedatabase!!.compileStatement(sql)
        for(a in arrayList.indices){
            stmt.bindString(1, arrayList[a].Name)
            stmt.bindString(2, arrayList[a].Address)
            stmt.bindString(3, arrayList[a].PhoneNumber)
            stmt.bindString(4, arrayList[a].AlamatEmail)
            stmt.bindString(5, arrayList[a].keterangan)
            stmt.execute()
            stmt.clearBindings()
        }
        sqlitedatabase!!.setTransactionSuccessful()
        sqlitedatabase!!.endTransaction()

    }

    fun checkdbexist(): Boolean{
        var result = false
        val cursor = sqlitedatabase!!.rawQuery("SELECT "+ COLUMN_NAME+" FROM "+ TABLE_NAME,null)
        //cursor.moveToFirst()
        Log.d("logging", cursor.count.toString())
        if(cursor.count>0){
            Log.d("logging", "entering this")
            result = true
        }
        cursor.close()
        return result
    }

    fun getalldata(): ArrayList<ModelDataPelanggan>{
        var modelDataPelanggan : ModelDataPelanggan
        val arrayList = ArrayList<ModelDataPelanggan>()
        val cursor = sqlitedatabase!!.rawQuery("SELECT * FROM "+ TABLE_NAME, null)
        cursor.moveToFirst()
        if(cursor.count > 0){
            do {
                modelDataPelanggan = ModelDataPelanggan()
                modelDataPelanggan.IdDatabase = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_ID_DATABASE))
                modelDataPelanggan.Name= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                modelDataPelanggan.Address= cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_ADDRESS))
                modelDataPelanggan.PhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_PHONE_NUMBER))
                modelDataPelanggan.AlamatEmail = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_EMAIL_ADDRESS))
                modelDataPelanggan.keterangan = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_KETERANGAN))
                val resolver: ContentResolver = context.contentResolver
                val cursorphoto = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Phone.NUMBER+"=?",
                    arrayOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE_NUMBER))), null)
                if (cursorphoto!!.count > 0) {
                    cursorphoto.moveToFirst()
                    do{
                        modelDataPelanggan.pProfile = cursorphoto.getString(cursorphoto.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI))
                    }while (cursorphoto.moveToNext())
                }
                cursorphoto.close()
                arrayList.add(modelDataPelanggan)
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return arrayList
    }

    fun getdetailbyname(nama:String): ModelDataPelanggan{
        Log.d("logging","namamu "+nama)
        var modelDataPelanggan : ModelDataPelanggan? = null
        val cursor = sqlitedatabase!!.rawQuery("SELECT * FROM "+ TABLE_NAME+" WHERE "+
            COLUMN_NAME+" Like'%"+nama.trim { it<=' ' }+"%'", null)
        Log.d("logging",cursor.count.toString())
        cursor.moveToFirst()
        if(cursor.count > 0){
            do {
                modelDataPelanggan = ModelDataPelanggan()
                modelDataPelanggan!!.IdDatabase = cursor.getInt(cursor.getColumnIndexOrThrow(
                    COLUMN_ID_DATABASE))
                modelDataPelanggan!!.Name= cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                modelDataPelanggan!!.Address = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_ADDRESS))
                modelDataPelanggan!!.PhoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_PHONE_NUMBER))
                modelDataPelanggan!!.AlamatEmail = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_EMAIL_ADDRESS))
                modelDataPelanggan!!.keterangan = cursor.getString(cursor.getColumnIndexOrThrow(
                    COLUMN_KETERANGAN))
                cursor.moveToNext()
            }while (!cursor.isAfterLast)
        }
        cursor.close()
        return modelDataPelanggan!!
    }

    fun updateData(modelDataPelanggan: ModelDataPelanggan){
        var args = ContentValues()
        args.put(COLUMN_NAME, modelDataPelanggan.Name)
        args.put(COLUMN_ADDRESS, modelDataPelanggan.Address)
        args.put(COLUMN_PHONE_NUMBER, modelDataPelanggan.PhoneNumber)
        args.put(COLUMN_EMAIL_ADDRESS, modelDataPelanggan.AlamatEmail)
        args.put(COLUMN_KETERANGAN, modelDataPelanggan.keterangan)
        sqlitedatabase!!.update(TABLE_NAME, args, COLUMN_ID_DATABASE+"=' "+modelDataPelanggan.IdDatabase+"'", null)

    }


    fun deleteData(idDatabase: Int){
        sqlitedatabase!!.delete(TABLE_NAME, COLUMN_ID_DATABASE+"=' "+idDatabase+"'", null)
    }

    fun insertProduct(arrayList: ArrayList<ProductModel>){
        val sql = "INSERT INTO "+Config.TABLE_PRODUCT+"("+Config.COLUMN_PRODUCT_NAME+", "+
                Config.COLUMN_PRODUCT_PRICE+") VALUES (?, ?)"
        sqlitedatabase!!.beginTransaction()
        val statement= sqlitedatabase!!.compileStatement(sql)
        arrayList.forEach{ product ->
            statement.bindString(1, product.productName)
            statement.bindDouble(2, product.productPrice)
            statement.execute()
            statement.clearBindings()
        }
        sqlitedatabase!!.setTransactionSuccessful()
        sqlitedatabase!!.endTransaction()
    }

    fun checktableproductexist(): Boolean{
        var result = false
        val cursor = sqlitedatabase!!.rawQuery("SELECT "+ Config.COLUMN_PRODUCT_NAME+" FROM "+ Config.TABLE_PRODUCT,null)
        //cursor.moveToFirst()
        if(cursor.count>0){
            result = true
        }
        cursor.close()
        return result
    }

    fun getAllProduct():ArrayList<ProductModel>{
        var result : ArrayList<ProductModel> = ArrayList<ProductModel>()
        var query = "SELECT * FROM "+Config.TABLE_PRODUCT
        val cursor = sqlitedatabase!!.rawQuery(query, null)
        if((cursor!=null) && (cursor.count>0)){
            while(cursor.moveToNext()){
                var modelProduct = ProductModel()
                modelProduct.productId = cursor.getInt(cursor.getColumnIndexOrThrow(Config.COLUMN_ID_DATABASE))
                modelProduct.productName = cursor.getString(cursor.getColumnIndexOrThrow(Config.COLUMN_PRODUCT_NAME))
                modelProduct.productPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(Config.COLUMN_PRODUCT_PRICE))
                result.add(modelProduct)
            }
            cursor.close()
        }
        return result
    }

    fun getDetailProduct(productName:String): ProductModel{
        lateinit var result : ProductModel
        val query = "SELECT * FROM "+Config.TABLE_PRODUCT+" WHERE "+Config.COLUMN_PRODUCT_NAME+
                " LIKE '%"+productName.trim{it<=' '}+"%'"
        val cursor = sqlitedatabase!!.rawQuery(query, null)
        if((cursor!=null) && (cursor.count > 0)){
            while (cursor.moveToNext()){
                result = ProductModel()
                result.productId = cursor.getInt(cursor.getColumnIndexOrThrow(Config.COLUMN_ID_DATABASE))
                result.productName = cursor.getString(cursor.getColumnIndexOrThrow(Config.COLUMN_PRODUCT_NAME))
                result.productPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(Config.COLUMN_PRODUCT_PRICE))
            }
            cursor.close()
        }
        return result
    }
    fun updateProduct(productModel: ProductModel){
        var args = ContentValues()
        args.put(Config.COLUMN_PRODUCT_NAME, productModel.productName)
        args.put(Config.COLUMN_PRODUCT_PRICE, productModel.productPrice)
        sqlitedatabase!!.update(Config.TABLE_PRODUCT, args, COLUMN_ID_DATABASE +
        " '="+productModel.productId+"'", null)
    }

    fun deleteProduct(idProduct:Int){
        sqlitedatabase!!.delete(Config.TABLE_PRODUCT, COLUMN_ID_DATABASE+
        "'="+idProduct+"'", null)
    }



}