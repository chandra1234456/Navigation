package com.chandra.practice.navigation.alertdialog

import android.content.Context
import android.view.LayoutInflater
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.chandra.practice.navigation.R
import com.google.android.material.textview.MaterialTextView


fun <T> showLanguageSelectionDialog(
    context : Context ,
    dialogTitle : String ,
    itemList : List<T> ,
    selectedItem : (T) -> Unit ,

    ) {
    val builder = AlertDialog.Builder(context , R.style.CustomAlertDialog).create()
    val languageView = LayoutInflater.from(context).inflate(R.layout.layout_language_selection , null)
    val title = languageView.findViewById<MaterialTextView>(R.id.tvLanguageTitle)
    builder.setView(languageView)




    builder.setCancelable(false)
    builder.show()


}