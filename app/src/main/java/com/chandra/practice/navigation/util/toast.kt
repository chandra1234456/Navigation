package com.chandra.practice.navigation.util

import android.content.Context
import android.widget.Toast

fun toastMessage(text : String , context : Context) {
    Toast.makeText(context , text , Toast.LENGTH_LONG).show()
}