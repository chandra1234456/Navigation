package com.chandra.practice.navigation.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Process
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.chandra.practice.navigation.Common.showBottomSheetDialog
import com.chandra.practice.navigation.MainActivity
import com.chandra.practice.navigation.R
import com.chandra.practice.navigation.databinding.FragmentSplashBinding
import com.chandra.practice.navigation.util.locationUtil.LocationHelper
import kotlin.system.exitProcess


class SplashFragment : Fragment() {
    private lateinit var splashBinding : FragmentSplashBinding

    private lateinit var locationHelper: LocationHelper
    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        splashBinding = FragmentSplashBinding.inflate(layoutInflater)
        locationHelper = LocationHelper(requireContext())

        // Check for location permission and request location
        locationHelper.requestLocationPermission(1)
        if (isUsbDebuggingEnabled(requireContext()) || isDeveloperOptionsEnabled(requireContext())) {
            showBottomSheetDialog(
                    requireContext() ,
                    R.drawable.ic_warning_circle ,
                    "Debugging Mode Detected" ,
                    "We noticed that you have enabled USB Debugging Mode on your device which can interfere with the normal app operations. Its recommended to disable this mode to ensure smooth functioning of the app" ,
                    onNegativeOnClick = {
                       // restartApplication(requireContext())
                        requireActivity().finish()
                    } ,
                    onPositiveOnClick = {
                        directToDeveloperOptions()
                    }
                                 )
        }else{
            newDestination()
        }

        return splashBinding.root
    }

    private fun directToDeveloperOptions() {
        newDestination()
    }

    private fun newDestination() {
        Handler().postDelayed({
            findNavController().navigate(R.id.homeFragment)
        } , 2000)
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).setUpBottomNavigationView(View.GONE , false)
            locationHelper.registerLocationReceiver()
        }
    }
    fun isUsbDebuggingEnabled(context: Context): Boolean {
        return Settings.Global.getInt(
                context.contentResolver,
                Settings.Global.ADB_ENABLED,
                0
                                     ) == 1
    }

    fun isDeveloperOptionsEnabled(context: Context): Boolean {
        return Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                0
                                     ) == 1
    }

    fun restartApplication(context :Context) {
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

    override fun onPause() {
        super.onPause()
        // Unregister receiver to stop receiving location updates
        locationHelper.unregisterLocationReceiver()
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, get location
            locationHelper.getLocation()
        } else {
            // Permission denied
            Toast.makeText(requireContext(), "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
        // Set the status bar color for the splash fragment
        if (activity != null) {
            val window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext()  , R.color.status_bar)
        }
    }

    override fun onStop() {
        super.onStop()
        // Optionally reset the status bar color when leaving the fragment
        if (activity != null) {
            val window = requireActivity().window
            window.statusBarColor =
                ContextCompat.getColor(requireContext() , R.color.status_bar)
        }
    }

}