package com.chandra.practice.navigation.alertdialog

import com.google.gson.annotations.SerializedName

data class LanguageModel(
    @SerializedName("languageText")
    var languageText : String ,
    @SerializedName("languageIcon")
    var languageIcon : String ,
                        )
