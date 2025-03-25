package com.chandra.practice.navigation.navargs


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    @SerializedName("age")
    var age: String?,
    @SerializedName("course")
    var course: String?,
    @SerializedName("name")
    var name: String?
) :Parcelable