package com.chandra.practice.navigation.settings.adapter

class HeaderItem(
    var headerName: String? = null
              ) : ListItem() {

    override fun getType(): Int {
        return TYPE_HEADER_NAME
    }
}