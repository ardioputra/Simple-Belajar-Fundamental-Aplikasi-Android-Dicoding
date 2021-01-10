package com.example.favoriteapp.db

import android.net.Uri
import android.provider.BaseColumns
import com.example.favoriteapp.db.DatabaseContract.UserColumns.Companion.TABLE_NAME

object DatabaseContract {

    const val SCHEME = "content"
    const val AUTHORITY = "com.example.githubapplication"

    class UserColumns : BaseColumns{
        companion object{
            const val TABLE_NAME = "table_fav"
            const val USER_ID = "extra_id"
            const val USER_PIC = "user_pic"
            const val USER_NAME = "user_name"
            const val USER_REAL = "user_real"
            const val USER_TYPE = "user_type"
            const val USER_LOCATION = "user_location"
            const val USER_COMPANY = "user_company"
            const val USER_REPOS = "user_repos"
        }
    }

    val CONTENT_URI: Uri = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()
}