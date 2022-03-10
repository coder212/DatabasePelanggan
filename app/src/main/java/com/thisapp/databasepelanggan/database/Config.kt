package com.thisapp.databasepelanggan.database

class Config {

    companion object {
        const val DATABASE_NAME = "DaatabasePelanggan.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "DataPelanggan"
        const val COLUMN_ID_DATABASE = "ID_DATABASE"
        const val COLUMN_NAME = "NAMA"
        const val COLUMN_ADDRESS = "ALAMAT"
        const val COLUMN_PHONE_NUMBER = "PHONE_NUMBER"
        const val COLUMN_EMAIL_ADDRESS = "ALAMAT_EMAIL"
        const val COLUMN_KETERANGAN = "KETERANGAN"
        const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME+"("+ COLUMN_ID_DATABASE+
                " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COLUMN_NAME+" TEXT NOT NULL, "+
                COLUMN_ADDRESS+" TEXT, "+ COLUMN_PHONE_NUMBER+" TEXT, "+ COLUMN_EMAIL_ADDRESS+" TEXT, "+
                COLUMN_KETERANGAN+" TEXT);"
        const val DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME

    }
}