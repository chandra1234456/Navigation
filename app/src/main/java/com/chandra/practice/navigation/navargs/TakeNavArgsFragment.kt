package com.chandra.practice.navigation.navargs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chandra.practice.navigation.MainActivity
import com.chandra.practice.navigation.databinding.FragmentTakeNavArgsBinding

class TakeNavArgsFragment : Fragment() {
    private lateinit var takeNavArgsBinding : FragmentTakeNavArgsBinding
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        takeNavArgsBinding = FragmentTakeNavArgsBinding.inflate(layoutInflater)
        takeNavArgsBinding.buttonSubmit.setOnClickListener {
            val name = takeNavArgsBinding.editText1.text.toString()
            val age = takeNavArgsBinding.editText2.text.toString()
            val study = takeNavArgsBinding.editText3.text.toString()
            val userInfo = UserInfo(age ,study,name)
            val userList = listOf(UserInfo("12","HELLO","NEW"),UserInfo("14","HELLO","OLD"))
            val action = TakeNavArgsFragmentDirections.actionTakeNavArgsFragmentToDisplayNavArgsFragment(userInfo)
            findNavController().navigate(action)
        }
        return takeNavArgsBinding.root
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity){
            (requireActivity() as MainActivity).setUpBottomNavigationView(View.GONE,false)
        }
    }

}