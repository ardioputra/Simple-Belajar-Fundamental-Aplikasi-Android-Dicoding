package com.example.githubapplication.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_COMPANY
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_ID
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_LOCATION
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_NAME
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_PIC
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_REAL
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_REPOS
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.USER_TYPE

internal class DatabaseHelper(context: Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
){
    companion object{
        private const val DATABASE_NAME = "userfavdb"
        private const val DATABASE_VERSION = 4
        private const val SQL_CREATE_TABLE_NOTE = "CREATE TABLE $TABLE_NAME" +
                " ($USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $USER_NAME TEXT NOT NULL," +
                " $USER_PIC TEXT NOT NULL," +
                " $USER_REAL TEXT NOT NULL," +
                " $USER_TYPE TEXT NOT NULL," +
                " $USER_LOCATION TEXT NOT NULL," +
                " $USER_COMPANY TEXT NOT NULL," +
                " $USER_REPOS TEXT NOT NULL)"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_NOTE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}