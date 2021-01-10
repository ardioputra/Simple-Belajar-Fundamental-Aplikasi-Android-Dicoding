package com.example.favoriteapp.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DataUser(
    var avatar: String? = null,
    var name: String? = null,
    var realName: String? = null,
    var type: String? = null,
    var location: String? = null,
    var company : String? = null,
    var repos: String? = null,
    var favorite: String? = null
): Parcelable
