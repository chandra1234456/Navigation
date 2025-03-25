package com.chandra.practice.navigation

import android.content.Context
import android.view.LayoutInflater
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

object Common {
    /**
     * This Custom Bottom Sheet Dialog for Using Warning and Info Display Purpose
     */
    fun showBottomSheetDialog(
        context : Context ,
        imageIcon : Int? = null ,
        titleText : String? ,
        contextMessage : String? ,
        onNegativeOnClick : () -> Unit ,
        onPositiveOnClick : () -> Unit ,
                             ) {

        val inflater = LayoutInflater.from(context)
        val bottomSheetBinding = inflater.inflate(R.layout.layout_bottom_sheet , null)
        // Create and show the BottomSheetDialog
        val bottomSheetDialog = BottomSheetDialog(context)
        bottomSheetDialog.setContentView(bottomSheetBinding)

        val bottomSheetPositive : MaterialButton =
            bottomSheetBinding.findViewById(R.id.bottomSheetPositive)
        val bottomSheetNegative : MaterialButton =
            bottomSheetBinding.findViewById(R.id.bottomSheetNegative)
        val bottomSheetIcon : ImageView = bottomSheetBinding.findViewById(R.id.bottomSheetIcon)
        val bottomSheetTitle : MaterialTextView =
            bottomSheetBinding.findViewById(R.id.bottomSheetTitle)
        val bottomSheetContextMessage : MaterialTextView =
            bottomSheetBinding.findViewById(R.id.bottomSheetContextMessage)


        val iconResource = imageIcon ?: R.drawable.ic_star
        bottomSheetIcon.setImageResource(iconResource)
        bottomSheetTitle.text = titleText
        bottomSheetContextMessage.text = contextMessage

        bottomSheetNegative.setOnClickListener {
            onNegativeOnClick()
            bottomSheetDialog.dismiss()
        }
        bottomSheetPositive.setOnClickListener {
            onPositiveOnClick()
            bottomSheetDialog.dismiss()
        }

        // Optional: Set dialog behavior (e.g., full screen, wrap content, etc.)
        //bottomSheetDialog.behavior.isHideable = false
       // bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
         bottomSheetDialog.setCancelable(false)
        // Show the bottom sheet dialog
        bottomSheetDialog.show()
    }
}