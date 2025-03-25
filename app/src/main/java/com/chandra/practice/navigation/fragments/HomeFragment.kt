package com.chandra.practice.navigation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chandra.practice.navigation.MainActivity
import com.chandra.practice.navigation.R
import com.chandra.practice.navigation.databinding.FragmentHomeBinding
import java.util.Date
import java.util.concurrent.TimeUnit

class HomeFragment : Fragment() {
    private lateinit var homeBinding : FragmentHomeBinding
    private var sharedPreferencesLogin : SharedPreferences? = null

    private var timerRunning = false
    private var secondsElapsed = 0L

    // Handler to update the UI every second
    private val handler = Handler()
    // Runnable that updates the timer
    private val updateTimerRunnable = object : Runnable {
        override fun run() {
            if (timerRunning) {
                secondsElapsed++
                updateTimerText()
                handler.postDelayed(this, 1000) // Re-run every second
            }
        }
    }
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        homeBinding = FragmentHomeBinding.inflate(layoutInflater)
        sharedPreferencesLogin =
            requireContext().getSharedPreferences("LoginValues" , Context.MODE_PRIVATE)

        homeBinding.extendedFloatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.takeNavArgsFragment)
        }
        val data = retrieveLoginValues("NEW")
        if (data.isNullOrEmpty()) {
            val currentDate = Date()
            println("Current date and time: $currentDate")
            loginValues("NEW" , currentDate.toString())
           // startTimer()
        } else {
          //  homeBinding.full.text = data
           // startTimer()
        }
        return homeBinding.root
    }
    private fun startTimer() {
        if (!timerRunning) {
            timerRunning = true
            handler.post(updateTimerRunnable) // Start updating the timer every second
        }
    }

    private fun stopTimer() {
        timerRunning = false
        handler.removeCallbacks(updateTimerRunnable) // Stop updating the timer
    }
    private fun updateTimerText() {
        val hours = TimeUnit.SECONDS.toHours(secondsElapsed)
        val minutes = TimeUnit.SECONDS.toMinutes(secondsElapsed) % 60
        val seconds = secondsElapsed % 60

        val timeText = String.format("%002d:%002d:%02d", hours, minutes, seconds)
        // Update the individual TextViews
        homeBinding.hh.text = String.format("%02d", hours) // Display hours
        homeBinding.mm.text = String.format("%02d", minutes) // Display minutes
        homeBinding.ss.text = String.format("%02d", seconds) // Display seconds
    //    homeBinding.full.text = timeText
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).setUpBottomNavigationView(View.VISIBLE , true)
           // startTimer()
        }
    }

    fun loginValues(savedName : String , name : String) {
        val editor = sharedPreferencesLogin?.edit()
        editor?.putString(savedName , name)
        editor?.apply()
    }

    fun retrieveLoginValues(savedName : String) : String? {
        return sharedPreferencesLogin?.getString(savedName , null)
    }
    override fun onPause() {
        super.onPause()
        // Optionally stop the timer when the activity is paused
        stopTimer()
    }

    override fun onStart() {
        super.onStart()
        // Set the status bar color for the splash fragment
        if (activity != null) {
            val window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext()  , R.color.home_status_bar_new)
        }
    }

    override fun onStop() {
        super.onStop()
        // Optionally reset the status bar color when leaving the fragment
        if (activity != null) {
            val window = requireActivity().window
            window.statusBarColor =
                ContextCompat.getColor(requireContext() , R.color.home_status_bar_new)
        }
    }
}