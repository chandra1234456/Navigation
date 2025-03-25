package com.chandra.practice.navigation.settings.adapter

class GeneralItem(
    var settingItem: SettingsItems? = null
                 ) : ListItem() {

    override fun getType(): Int {
        return TYPE_GENERAL
    }
}