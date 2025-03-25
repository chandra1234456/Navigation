package com.chandra.practice.navigation.util.locationUtil

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.core.content.ContextCompat
import android.widget.Toast
import com.chandra.practice.navigation.util.toastMessage

class LocationHelper(private val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    private val locationReceiver: LocationReceiver = LocationReceiver()

    // Request location if permission is granted
    fun requestLocationPermission(requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            
            ActivityCompat.requestPermissions(
                context as Activity ,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                requestCode
            )
        } else {
            getLocation()
        }
    }

    // Get the last known location
    fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                    context ,
                    Manifest.permission.ACCESS_FINE_LOCATION
                                              ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context ,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                                                                                                                          ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            sendLocationUpdate(location)
        }
    }

    // Send location data through LocalBroadcast
    private fun sendLocationUpdate(location: Location?) {
        val intent = Intent("com.example.location.UPDATE")
        intent.putExtra("location", location)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    // Register receiver to listen for location updates
    fun registerLocationReceiver() {
        LocalBroadcastManager.getInstance(context).registerReceiver(
            locationReceiver,
            IntentFilter("com.example.location.UPDATE")
        )
    }

    // Unregister receiver to stop receiving updates
    fun unregisterLocationReceiver() {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(locationReceiver)
    }

    // Handle the BroadcastReceiver logic
    inner class LocationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val location = intent?.getParcelableExtra<Location>("location")
            if (location == null) {
                // Location is null, show Snackbar error
                toastMessage("Location not available",context!!)
            } else {
                // Handle location here, for example, show a toast
                val latitude = location.latitude
                val longitude = location.longitude
                Toast.makeText(context, "Location: $latitude, $longitude", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Show Snackbar message
    private fun showSnackbar(message: String) {
       // val rootView = (context as Activity).findViewById(android.R.id.content)
      //  Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }
}
