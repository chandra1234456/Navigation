package com.chandra.practice.navigation.settings

import SettingsListHelper
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.chandra.practice.navigation.MainActivity
import com.chandra.practice.navigation.R
import com.chandra.practice.navigation.alertdialog.LanguageModel
import com.chandra.practice.navigation.alertdialog.showLanguageSelectionDialog
import com.chandra.practice.navigation.databinding.FragmentSettingsBinding
import com.chandra.practice.navigation.settings.adapter.GeneralItem
import com.chandra.practice.navigation.settings.adapter.HeaderItem
import com.chandra.practice.navigation.settings.adapter.ListItem
import com.chandra.practice.navigation.settings.adapter.SettingsAdapter
import com.chandra.practice.navigation.settings.adapter.SettingsItems


class SettingsFragment : Fragment() , SettingsInterface {
    private lateinit var settingsBinding : FragmentSettingsBinding
    private lateinit var callback : OnBackPressedCallback
    private lateinit var settingsAdapter : SettingsAdapter
    private var consolidatedList : ArrayList<ListItem> = ArrayList()

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle? ,
                             ) : View {
        settingsBinding = FragmentSettingsBinding.inflate(layoutInflater)
        callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (requireActivity() is MainActivity) {
                    if (! (requireActivity() as MainActivity).closeDrawer()) {
                        findNavController().navigate(R.id.homeFragment)
                    }
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner , callback)

        settingsAdapterData(SettingsListHelper.getSettingsItems())
        return settingsBinding.root
    }

    private fun settingsAdapterData(myOptions : ArrayList<SettingsItems>) {
        val groupedHashMap : HashMap<String , ArrayList<SettingsItems>> =
            groupDataIntoHashMap(myOptions)

        for (item in groupedHashMap.keys) {
            val headerItem = HeaderItem()
            headerItem.headerName = item
            consolidatedList.add(headerItem)
            for (settingItem in groupedHashMap[item] !!) {
                val generalItem = GeneralItem()
                generalItem.settingItem = settingItem //setBookingDataTabs(bookingDataTabs);
                consolidatedList.add(generalItem)
            }
        }

        settingsAdapter = SettingsAdapter(consolidatedList , this)
        settingsBinding.settingsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL , false)
        settingsBinding.settingsRecyclerView.adapter = settingsAdapter
    }

    override fun onResume() {
        super.onResume()
        if (requireActivity() is MainActivity) {
            (requireActivity() as MainActivity).setUpBottomNavigationView(View.GONE , false)

        }
    }

    private fun groupDataIntoHashMap(settingsItems : List<SettingsItems>) : HashMap<String , ArrayList<SettingsItems>> {
        val groupedHashMap : HashMap<String , ArrayList<SettingsItems>> =
            HashMap()

        for (item in settingsItems) {
            val hashMapKey : String = item.heading.toString()
            if (groupedHashMap.containsKey(hashMapKey)) {
                // The key is already in the HashMap; add the pojo object
                // against the existing key.
                groupedHashMap[hashMapKey] !!.add(item)
            } else {
                // The key is not there in the HashMap; create a new key-value pair
                val list : MutableList<SettingsItems> = ArrayList()
                list.add(item)
                groupedHashMap[hashMapKey] = list as java.util.ArrayList<SettingsItems>
            }
        }
        return groupedHashMap
    }

    override fun onSettingsItemClicked(
        position : Int ,
        settingsItems : ListItem ,
        generalItem : SettingsItems ,
                                      ) {
        val usFlag = "\uD83C\uDDFA\uD83C\uDDF8" // 🇺🇸 United States
        val indiaFlag = "\uD83C\uDDEE\uD83C\uDDF3" // 🇮🇳 India
        val ukFlag = "\uD83C\uDDEC\uD83C\uDDE7" // 🇬🇧 United Kingdom
        val germanyFlag = "\uD83C\uDDE9\uD83C\uDDEA" // 🇩🇪 Germany
        val franceFlag = "\uD83C\uDDE8\uD83C\uDDF7" // 🇫🇷 France
        val japanFlag = "\uD83C\uDDEF\uD83C\uDDF5" // 🇯🇵 Japan
        if (generalItem.title == "LanguageModel") {
            val languageList = listOf(
                    LanguageModel("English",usFlag) ,
                    LanguageModel("Hindi" ,indiaFlag) ,
                    LanguageModel("British English",ukFlag) ,
                    LanguageModel("German" ,germanyFlag) ,
                    LanguageModel("French" ,franceFlag) ,
                    LanguageModel("Japanese" ,japanFlag)
                                     )
            showLanguageSelectionDialog(requireContext() , "Hello" , languageList) { item ->

            }
        }
    }


}