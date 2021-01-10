package com.example.aplikasigithubuser

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class User (
    var avatar:Int,
    var username:String,
    var name:String,
    var follower:String,
    var following:String,
    var location:String,
    var company:String,
    var respository:String
) : Parcelable