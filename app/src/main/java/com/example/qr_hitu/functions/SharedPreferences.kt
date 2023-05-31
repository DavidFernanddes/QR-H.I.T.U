package com.example.qr_hitu.functions



import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class SettingsManager(private val context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun saveSetting(key: String, value: String) {
        sharedPreferences.edit {
            putString(key, value)
            apply()
        }
    }

    fun getSetting(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}