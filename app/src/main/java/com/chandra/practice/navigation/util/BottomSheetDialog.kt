package com.chandra.practice.navigation.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chandra.practice.navigation.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetDialog : BottomSheetDialogFragment() {
    private lateinit var bottomSheetBinding : LayoutBottomSheetBinding
    override fun onCreateView(
        inflater : LayoutInflater ,
        container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        return bottomSheetBinding.root
    }

    fun showBottomSheetDialog(
        imageIcon : Int? = null ,
        titleText : String? ,
        contextMessage : String? ,
        onNegativeOnClick : () -> Unit ,
        onPositiveOnClick : () -> Unit ,
                             ) {
       val bottomSheetBinding = LayoutBottomSheetBinding.inflate(layoutInflater)
        bottomSheetBinding.apply {
            bottomSheetIcon.setImageResource(imageIcon !!)
            bottomSheetTitle.text = titleText
            bottomSheetContextMessage.text = contextMessage
        }
        bottomSheetBinding.bottomSheetNegative.setOnClickListener {
            onNegativeOnClick()
        }
        bottomSheetBinding.bottomSheetPositive.setOnClickListener {
            onPositiveOnClick()
        }
    }


}