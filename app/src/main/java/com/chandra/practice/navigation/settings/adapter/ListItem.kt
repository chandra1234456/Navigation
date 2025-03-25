package com.chandra.practice.navigation.settings.adapter

abstract class ListItem {

    companion object {
        const val TYPE_HEADER_NAME = 0
        const val TYPE_GENERAL = 1
    }

    abstract fun getType(): Int
}