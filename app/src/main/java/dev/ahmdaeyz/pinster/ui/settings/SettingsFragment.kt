package dev.ahmdaeyz.pinster.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import dev.ahmdaeyz.pinster.R
import dev.ahmdaeyz.pinster.R.xml.root_preferences

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(root_preferences, rootKey)
    }
}