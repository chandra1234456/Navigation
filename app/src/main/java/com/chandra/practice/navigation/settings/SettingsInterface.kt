package com.chandra.practice.navigation.settings

import com.chandra.practice.navigation.settings.adapter.ListItem
import com.chandra.practice.navigation.settings.adapter.SettingsItems

interface SettingsInterface {
    fun onSettingsItemClicked(position : Int , settingsItems : ListItem, generalItem : SettingsItems)
  //  fun onSettingsSwitchItemChanged(position : Int , settingsItems : ListItem)
}