package com.example.qr_hitu.functions

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

//  Funções para troca de lingua


//  Define o Locale que indica ao código qual stringResource tem de usar
fun setLocale(language: String, context: Context) {
    val locale = Locale(language)
    Locale.setDefault(locale)
    val resources = context.resources
    val configuration = Configuration(resources.configuration)
    configuration.setLocale(locale)
    resources.updateConfiguration(configuration, resources.displayMetrics)
}