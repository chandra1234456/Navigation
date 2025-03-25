package com.chandra.practice.navigation.settings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.recyclerview.widget.RecyclerView
import com.chandra.practice.navigation.R
import com.chandra.practice.navigation.gone
import com.chandra.practice.navigation.settings.SettingsInterface
import com.google.android.material.card.MaterialCardView


class SettingsAdapter(
    private val settingsList : ArrayList<ListItem> ,
    private val settingsInterface : SettingsInterface ,
                     ) :
        RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup , viewType : Int) : SettingsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ListItem.TYPE_GENERAL -> {
                val v1 : View =
                    inflater.inflate(R.layout.layout_without_header_in_settings , parent , false)
                SettingsViewHolder(v1)
            }

            ListItem.TYPE_HEADER_NAME -> {
                val v2 : View = inflater.inflate(R.layout.layout_settings_items , parent , false)
                SettingsViewHolder(v2)
            }

            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(holder : SettingsViewHolder , position : Int) {
        holder.bind(settingsList[position] , settingsInterface)
    }

    override fun getItemCount() : Int = settingsList.size

    // ViewHolder for both General and Date rows
    inner class SettingsViewHolder(private val view : View) : RecyclerView.ViewHolder(view) {

        // Assuming you have views in the layout to bind
        private val header : TextView = view.findViewById(R.id.header) // Example ID for the header
        private val tvTitle : TextView = view.findViewById(R.id.tvTitle)
        private val tvSubTitle : TextView = view.findViewById(R.id.tvSubTitle)
        private val ivNavigateIcon : ImageView = view.findViewById(R.id.ivNavigateIcon)
        private val switchCompact : SwitchCompat = view.findViewById(R.id.switchCompact)
        private val cardView : MaterialCardView = view.findViewById(R.id.cardView)

        fun bind(
            settingsItems : ListItem ,
            settingsInterface : SettingsInterface ,
                ) {
            when (settingsItems) {
                is GeneralItem -> {
                    // For GeneralItem
                    header.visibility = View.GONE
                    header.text = settingsItems.settingItem?.heading
                    tvTitle.text = settingsItems.settingItem?.title ?: ""
                    tvSubTitle.text = settingsItems.settingItem?.subTitle ?: ""
                    ivNavigateIcon.visibility =
                        if (settingsItems.settingItem?.arrowEnabled == true) View.VISIBLE else View.GONE
                    switchCompact.visibility =
                        if (settingsItems.settingItem?.radioEnabled == true) View.VISIBLE else View.GONE
                }

                is HeaderItem -> {
                    // For DateItem
                    header.visibility = View.VISIBLE
                    header.text = settingsItems.headerName
                    cardView.gone()
                }
            }
            ivNavigateIcon.setOnClickListener {
                // Make sure settingItem is not null before passing it
                if (settingsItems is GeneralItem && settingsItems.settingItem != null) {
                    settingsInterface.onSettingsItemClicked(
                            adapterPosition , settingsItems ,
                            settingsItems.settingItem !!
                                                           )
                }
            }

            switchCompact.setOnClickListener {
                // Handle click for switchCompact, assuming you're passing a new GeneralItem or its settingItem
                if (settingsItems is GeneralItem) {
                    settingsItems.settingItem?.let { it1 ->
                        settingsInterface.onSettingsItemClicked(
                                adapterPosition ,
                                settingsItems ,
                                it1
                                                               )
                    }
                }
            }
        }

    }

    override fun getItemViewType(position : Int) : Int {
        return settingsList[position].getType()
    }
}
