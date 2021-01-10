package com.example.githubapplication.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.githubapplication.db.DatabaseContract.AUTHORITY
import com.example.githubapplication.db.DatabaseContract.CONTENT_URI
import com.example.githubapplication.db.DatabaseContract.UserColumns.Companion.TABLE_NAME
import com.example.githubapplication.db.UserHelper

class FavProvider : ContentProvider() {

    companion object{
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private const val FAV = 1
        private const val ID = 2
        private lateinit var usHelper : UserHelper

        init{
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAV)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", ID)
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when (ID){
            sUriMatcher.match(uri) -> usHelper.deleteById(uri.lastPathSegment.toString())
            else->0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(FAV){
            sUriMatcher.match(uri) -> usHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun onCreate(): Boolean {
        usHelper = UserHelper.getInstance(context as Context)
        usHelper.open()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)){
            FAV -> usHelper.queryAll()
            ID -> usHelper.queryById(uri.lastPathSegment.toString())
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        val updated: Int = when(ID){
            sUriMatcher.match(uri) -> usHelper.update(
                uri.lastPathSegment.toString(),
                values
            )
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return updated
    }

}