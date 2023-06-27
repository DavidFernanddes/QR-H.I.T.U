package com.example.qr_hitu.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.functions.ScaffoldLayouts
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.setLocale
import com.example.qr_hitu.theme.QRHITUTheme
import java.util.Locale

//  Neste ficheiro é onde toda a aplicação se liga e todas as partes importantes são definidas
@Composable
fun QrHituApp() {
    val localContext = LocalContext.current
    //  Inicialização do navegador
    val navController = rememberNavController()
    //  Inicialização das settings
    val settingsManager = SettingsManager(localContext)
    //  Inicialização dos viewModels
    val viewModel1 = viewModel<ViewModel1>()
    val viewModel2 = viewModel<ViewModel2>()
    val viewModelSA = viewModel<ScannerViewModel>()
    val viewModelMF = viewModel<MalfunctionViewModel>()
    //  Estado de troca de tema
    val switch = remember { mutableStateOf("") }
    //  Verificação de qual idioma está selecionado
    val theme by rememberUpdatedState(if (switch.value == "") { settingsManager.getSetting("Theme", "" ) } else switch.value)
    setLocale(if (settingsManager.getSetting("Language", "pt") == "pt") "pt" else "en", localContext)

    //  Tema da app
    QRHITUTheme(
        content = {
            //  Resto da app
            ScaffoldLayouts(navController, settingsManager, viewModel1, viewModel2, viewModelSA, viewModelMF, switch)
        },
        theme = theme
    )

}
