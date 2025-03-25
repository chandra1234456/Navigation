package com.chandra.practice.navigation.navargs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chandra.practice.navigation.MainActivity
import com.chandra.practice.navigation.databinding.FragmentDisplayNavArgsBinding

class DisplayNavArgsFragment : Fragment() {
    private val args : DisplayNavArgsFragmentArgs by navArgs()  // Safe Args property delegate
    private lateinit var displayNavArgsBinding : FragmentDisplayNavArgsBinding

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        // Use Safe Args to get the argument
        val userInfo = args.userInfo
        // Use the received userInfo
        println("User Info: ${userInfo.name}, ${userInfo.age} ,${userInfo.course}")
        displayNavArgsBinding.displayData.text =
            "NAME : ${userInfo.name} \n AGE : ${userInfo.age} \n Course : ${userInfo.course}"
    }

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        displayNavArgsBinding = FragmentDisplayNavArgsBinding.inflate(layoutInflater)
        return displayNavArgsBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).setUpBottomNavigationView(View.GONE , false)
        }
    }

}