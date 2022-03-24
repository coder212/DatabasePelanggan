package com.thisapp.databasepelanggan.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.thisapp.databasepelanggan.database.Config.Companion.DATABASE_NAME
import com.thisapp.databasepelanggan.database.Config.Companion.DATABASE_VERSION
import com.thisapp.databasepelanggan.database.Config.Companion.CREATE_TABLE
import com.thisapp.databasepelanggan.database.Config.Companion.DROP_TABLE
import com.thisapp.databasepelanggan.database.Config.Companion.CREATE_TABLE_CATATAN
import com.thisapp.databasepelanggan.database.Config.Companion.CREATE_TABLE_PRODUCT
import com.thisapp.databasepelanggan.database.Config.Companion.CREATE_TABLE_ITEM
import com.thisapp.databasepelanggan.database.Config.Companion.DROP_TABLE_PRODUCT
import com.thisapp.databasepelanggan.database.Config.Companion.DROP_TABLE_CATATAN
import com.thisapp.databasepelanggan.database.Config.Companion.DROP_TABLE_ITEM

class Helper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        //create
        val db = db!!
        db.execSQL(CREATE_TABLE)
        db.execSQL(CREATE_TABLE_PRODUCT)
        db.execSQL(CREATE_TABLE_CATATAN)
        db.execSQL(CREATE_TABLE_ITEM)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // upgrade TODO("Not yet implemented")
        val db = db!!
        db.execSQL(DROP_TABLE)
        db.execSQL(DROP_TABLE_PRODUCT)
        db.execSQL(DROP_TABLE_CATATAN)
        db.execSQL(DROP_TABLE_ITEM)
        onCreate(db)
    }

}
