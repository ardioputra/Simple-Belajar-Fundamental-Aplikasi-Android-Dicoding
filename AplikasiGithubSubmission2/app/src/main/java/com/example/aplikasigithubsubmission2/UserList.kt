package com.example.aplikasigithubsubmission2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserList(
    var avatar: String? = null,
    var name: String? = null,
    var realName: String? = null,
    var type: String? = null,
    var repos: String? = null,
    var company : String? = null,
    var follower: String? = null,
    var following: String? = null,
    var location: String? = null
): Parcelable