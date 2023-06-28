package com.example.qr_hitu.functions

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit

//  Class para aceder ás SharedPreferences
class SettingsManager(context: Context) {
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    //  Salva uma setting
    fun saveSetting(key: String, value: String) {
        sharedPreferences.edit {
            putString(key, value)
            apply()
        }
    }

    //  Busca o valor de uma setting
    fun getSetting(key: String, defaultValue: String): String {
        return sharedPreferences.getString(key, defaultValue) ?: defaultValue
    }
}


//  Função para ir buscar a lista de recentes á sharedPreferences
fun loadListFromSettings(settingsManager: SettingsManager): List<String> {
    val qrListAsString = settingsManager.getSetting("RecentsList", "")
    return if (qrListAsString.isBlank()) emptyList() else qrListAsString.split("//")
}


//  Função para guardar a lista de recentes na sharedPreferences
fun saveListToSettings(settingsManager: SettingsManager, qrList: List<String>) {
    val qrListAsString = qrList.joinToString("//")
    settingsManager.saveSetting("RecentsList", qrListAsString)
}