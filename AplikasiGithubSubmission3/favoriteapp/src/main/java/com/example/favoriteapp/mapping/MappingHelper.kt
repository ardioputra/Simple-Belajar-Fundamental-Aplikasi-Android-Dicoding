package com.example.favoriteapp.mapping

import android.database.Cursor
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_COMPANY
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_LOCATION
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_NAME
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_PIC
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_REAL
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_REPOS
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.USER_TYPE
import com.example.favoriteapp.entity.DataFavorite
import java.util.ArrayList

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<DataFavorite> {
        val favList = ArrayList<DataFavorite>()

        notesCursor?.apply {
            while (moveToNext()) {
                val avatar = getString(getColumnIndexOrThrow(USER_PIC))
                val username = getString(getColumnIndexOrThrow(USER_NAME))
                val name = getString(getColumnIndexOrThrow(USER_REAL))
                val type = getString(getColumnIndexOrThrow(USER_TYPE))
                val location = getString(getColumnIndexOrThrow(USER_LOCATION))
                val company = getString(getColumnIndexOrThrow(USER_COMPANY))
                val repository = getString(getColumnIndexOrThrow(USER_REPOS))

                favList.add(
                    DataFavorite(
                        avatar,
                        username,
                        name,
                        type,
                        location,
                        company,
                        repository
                    )
                )
            }
        }
        return favList
    }
}