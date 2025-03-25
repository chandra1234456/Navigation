package com.chandra.practice.navigation.settings.adapter


import com.google.gson.annotations.SerializedName

data class SettingsItems(
    @SerializedName("arrowEnabled")
    var arrowEnabled : Boolean ,
    @SerializedName("heading")
    var heading : String? ,
    @SerializedName("radioEnabled")
    var radioEnabled : Boolean ,
    @SerializedName("requiredText")
    var requiredText : String ,
    @SerializedName("subIcon")
    var subIcon : Int ,
    @SerializedName("subTitle")
    var subTitle : String ,
    @SerializedName("title")
    var title : String ,
                        )