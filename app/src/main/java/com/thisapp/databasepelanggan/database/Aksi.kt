package com.thisapp.databasepelanggan.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.thisapp.databasepelanggan.model.ModelDataPelanggan
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
        val arrayList = ArrayList<ModelDataPelanggan>()
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


}