package com.chandra.practice.navigation

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chandra.practice.navigation.databinding.ActivityMainBinding
import com.chandra.practice.navigation.fragments.FavoriteFragment
import com.chandra.practice.navigation.fragments.ProfileFragment
import com.chandra.practice.navigation.util.logUtil.LogType
import com.chandra.practice.navigation.util.logUtil.LogUtil
import com.chandra.practice.navigation.util.logUtil.LogUtil.log
import com.chandra.practice.navigation.util.networkUtil.ConnectivityReceiver
import com.chandra.practice.navigation.util.toastMessage
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener ,
                     ConnectivityReceiver.ConnectivityReceiverListener {
    private lateinit var mainBinding : ActivityMainBinding
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navController : NavController
    private lateinit var bottomNavigationView : BottomNavigationView
    private lateinit var actionBarDrawerToggle : ActionBarDrawerToggle
    private var mSnackBar : Snackbar? = null
    // Define the connectivity receiver instance
    private val connectivityReceiver = ConnectivityReceiver()
    private var backPressedOnce = false
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
        registerReceiver(
                ConnectivityReceiver() ,
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                        )
        // Set the listener for network connectivity changes
        ConnectivityReceiver.connectivityReceiverListener = this
        testLogUtil()
        //Navigation Drawer
        val navigationView : NavigationView = findViewById(R.id.navigationView)
        // Access the header view
        val headerView =
            navigationView.getHeaderView(0)  // 0 is the index of the header (it's always 0 for a single header)
        val userName : TextView = headerView.findViewById(R.id.user_name)
        val userEmail : TextView = headerView.findViewById(R.id.user_email)
        val appVersion : TextView = headerView.findViewById(R.id.versionText)
        navigationView.setNavigationItemSelectedListener(this)
        // Update the views
        userName.text = "New User Name"
        userEmail.text = "newuser@example.com"
        appVersion.text =
            "App Version :${this.packageManager.getPackageInfo(this.packageName , 0).versionName}"

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.naveHostContainer) as NavHostFragment
        navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNavigationView.setupWithNavController(navController)
        mainBinding.navIcon.setOnClickListener {
            openCloseNavigationDrawer()
        }

        bottomNavigationView.selectedItemId = R.id.bottomHome
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottomProfile -> {
                    navController.navigate(R.id.profileFragment)
                }

                R.id.bottomHome -> {
                    setUpBottomNavigationView(View.VISIBLE , true)
                    navController.navigate(R.id.homeFragment)
                }

                R.id.bottomFavorite -> {
                    navController.navigate(R.id.favoriteFragment)
                }

                else -> Toast.makeText(this@MainActivity , "Development" , Toast.LENGTH_SHORT)
                        .show()
            }
            true
        }
        // Handle the Navigation Drawer opening/closing if needed
        drawerLayout = findViewById(R.id.drawableLayout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
                this , drawerLayout , R.string.open , R.string.close
                                                     )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        //handleShortcutIntent(intent)
    }


    private fun openCloseNavigationDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }

    override fun onNewIntent(intent : Intent) {
        super.onNewIntent(intent)
        handleShortcutIntent(intent)
    }

    private fun handleShortcutIntent(intent : Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                // Do something based on the shortcut that was clicked
                val shortcutId = intent.getStringExtra("shortcutId")
                when (shortcutId) {
                    "shortcut_1" -> {
                        // Handle Shortcut 1 action
                        toastMessage("ONE" , this)
                    }

                    "shortcut_2" -> {
                        // Handle Shortcut 2 action
                        toastMessage("TWO" , this)
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        // check condition for drawer item with menu item
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }

    }

    override fun onSupportNavigateUp() : Boolean {
        val navController = findNavController(this , R.id.naveHostContainer)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun setUpMaterialToolBar(
        navigationIcon : Int? ,
        toolBarTitle : String? ,
        endNavIcon : Int? ,
        showLogoutIcon : Boolean = false ,
                            ) {
        val title = findViewById<MaterialTextView>(R.id.navTitle)
        val navIcon = mainBinding.navIcon
        val endIcon = mainBinding.navEndIcon
        if (showLogoutIcon) mainBinding.navLogoutIcon.visible()
        title?.let {
            it.visible()
            it.text = toolBarTitle
        } ?: kotlin.run {
            title.gone()
        }
    }

    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                setUpBottomNavigationView(View.VISIBLE , true)
                navController.navigate(R.id.homeFragment)
                bottomNavigationView.selectedItemId = R.id.bottomHome
            }

            R.id.nav_settings -> {
                navController.navigate(R.id.settingsFragment)
                //disable Bottom Navigation
                setUpBottomNavigationView(View.GONE , true)
            }

            R.id.nav_favorite -> {
                navController.navigate(R.id.favoriteFragment)
                bottomNavigationView.selectedItemId = R.id.bottomFavorite
            }

            R.id.nav_profile -> {
                navController.navigate(R.id.profileFragment)
                bottomNavigationView.selectedItemId = R.id.bottomProfile
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun closeDrawer() : Boolean {
        val drawer = findViewById<View>(R.id.drawableLayout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    fun hideDrawer() : Boolean {
        val drawer = findViewById<View>(R.id.drawableLayout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return true
        }
        return false
    }

    /* override fun onBackPressed() {
         super.onBackPressed()
         if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
             drawerLayout.closeDrawer(GravityCompat.START)
         } else {
             onBackPressedDispatcher.onBackPressed()
         }
     }*/

    fun setUpBottomNavigationView(visibility : Int , mainToolbar : Boolean) {
        mainBinding.bottomNavigation.visibility = visibility
        if (mainToolbar) mainBinding.materialToolbar.visible()
        else mainBinding.materialToolbar.gone()
        if (visibility == View.VISIBLE) mainBinding.drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        else mainBinding.drawableLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }


    // Override onBackPressed to handle back button press
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        val currentFragment = supportFragmentManager.findFragmentById(R.id.naveHostContainer)

        // Handle back press for Settings and Favorite Fragments
        if (currentFragment is ProfileFragment || currentFragment is FavoriteFragment) {
            if (! backPressedOnce) {
                backPressedOnce = true
                // Navigate to Home Fragment
                bottomNavigationView.selectedItemId = R.id.bottomHome
                navController.navigate(R.id.homeFragment)
                Toast.makeText(this , "Press again to exit" , Toast.LENGTH_SHORT).show()
            } else {
                // If pressed again, close the application
                super.onBackPressed()
            }
        } else {
            // If we are not on Settings or Favorite, perform the default back press action
            super.onBackPressed()
            finish()
        }
    }

    override fun onNetworkConnectionChanged(isConnected : Boolean) {
        showMessage(isConnected)
    }


    private fun showMessage(isConnected : Boolean) {
        val rootView = findViewById<FrameLayout>(android.R.id.content) // The root view
        val overlayView = findViewById<View>(R.id.overlay) // The overlay view

        // Dismiss the old Snack bar if it's showing
        mSnackBar?.dismiss()

        if (! isConnected) {
            // Show the "You are offline" Snack bar
            val messageToUser = "You are currently offline."
            mSnackBar = Snackbar.make(
                    rootView ,
                    messageToUser ,
                    Snackbar.LENGTH_INDEFINITE
                                     )
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()

            // Show the overlay to block interactions
            overlayView.visibility = View.VISIBLE

            // Disable all interactions with the UI by making the overlay clickable
            overlayView.setOnClickListener { /* Block interaction */ }
        } else {
            // Show the "Your internet connection was restored" Snack bar
            mSnackBar = Snackbar.make(
                    rootView ,
                    "Your internet connection was restored." ,
                    Snackbar.LENGTH_LONG
                                     )
            mSnackBar?.setBackgroundTint(resources.getColor(R.color.black))
            mSnackBar?.show()

            // Hide the overlay and restore interactions
            overlayView.visibility = View.GONE
        }
    }



    override fun onStart() {
        super.onStart()
        try {
            // Register the receiver for connectivity changes
            if (connectivityReceiver == null) {
                val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                registerReceiver(connectivityReceiver, intentFilter)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        try {
            // Unregister the receiver when the activity is stopped
            unregisterReceiver(connectivityReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the receiver to prevent memory leaks (optional: can also be done in onStop)
        try {
            unregisterReceiver(connectivityReceiver)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            // Register the receiver for network changes (if it wasn't already registered)
            if (connectivityReceiver == null) {
                val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
                registerReceiver(connectivityReceiver, intentFilter)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



    // Handle permission result

    private fun testLogUtil() {
        val appName = "Kotlin App"
        val versionCode = 1
        val isPremiumUser = true
        val userList = listOf("Alice" , "Bob" , "Charlie")

        log("appName" , appName , LogType.DEBUG)
        log("versionCode" , versionCode , LogType.INFO)
        log("isPremiumUser" , isPremiumUser , LogType.WARN)
        log("userList" , userList , LogType.ERROR)
    }
}