import com.chandra.practice.navigation.R
import com.chandra.practice.navigation.settings.adapter.SettingsItems

class SettingsListHelper {

    companion object {
        fun getSettingsItems(): ArrayList<SettingsItems> {
            val myOptions = ArrayList<SettingsItems>()

            // Add items to the list
            myOptions.add(
                    SettingsItems(
                            heading = "General",
                            radioEnabled = true,
                            arrowEnabled = false,
                            requiredText = "",
                            subTitle = "enable",
                            title = "Dark",
                            subIcon = R.drawable.ic_settings
                                 )
                         )

            myOptions.add(
                    SettingsItems(
                            heading = "General",
                            radioEnabled = false,
                            arrowEnabled = true,
                            requiredText = "",
                            subTitle = "English",
                            title = "LanguageModel",
                            subIcon = R.drawable.ic_settings
                                 )
                         )

            myOptions.add(
                    SettingsItems(
                            heading = "About",
                            radioEnabled = false,
                            arrowEnabled = true,
                            requiredText = "",
                            subTitle = "Balachandra Dasari",
                            title = "Developer",
                            subIcon = R.drawable.ic_settings
                                 )
                         )

            myOptions.add(
                    SettingsItems(
                            heading = "About",
                            radioEnabled = true,
                            arrowEnabled = false,
                            requiredText = "",
                            subTitle = "1.0",
                            title = "Version",
                            subIcon = R.drawable.ic_settings
                                 )
                         )
            myOptions.add(
                    SettingsItems(
                            heading = "About",
                            radioEnabled = true,
                            arrowEnabled = false,
                            requiredText = "",
                            subTitle = "New",
                            title = "Privacy Policy",
                            subIcon = R.drawable.ic_settings
                                 )
                         )


            return myOptions
        }
    }
}
