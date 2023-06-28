@file:Suppress("UNUSED_PARAMETER")

package com.example.qr_hitu.components


import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.functions.ConnectionState
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.WifiWarn
import com.example.qr_hitu.functions.connectivityState
import com.example.qr_hitu.screens.LoadingScreen
import com.example.qr_hitu.screens.adminScreens.AdminChoices
import com.example.qr_hitu.screens.adminScreens.create.QrCreateFinal
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase1
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase2
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminInfo
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminInfoUpdate
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminScreen
import com.example.qr_hitu.screens.adminScreens.tabLists.TabLayout
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfInfo
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfList
import com.example.qr_hitu.screens.adminScreens.tabLists.missingQrList.MissingQrList
import com.example.qr_hitu.screens.adminScreens.tabLists.recentScanList.RecentScanList
import com.example.qr_hitu.screens.adminScreens.transfer.ChooseQr
import com.example.qr_hitu.screens.adminScreens.transfer.TransferQr
import com.example.qr_hitu.screens.login.ForgotPass
import com.example.qr_hitu.screens.login.LoginScreen
import com.example.qr_hitu.screens.menu.About
import com.example.qr_hitu.screens.menu.Manual
import com.example.qr_hitu.screens.menu.SettingsOptions
import com.example.qr_hitu.screens.profScreens.missingQr.MQRLocal
import com.example.qr_hitu.screens.profScreens.PrimaryChoice
import com.example.qr_hitu.screens.profScreens.scanner.ScannerInput
import com.example.qr_hitu.screens.profScreens.scanner.ScannerTeachScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

//Função de navegação da app
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun QrHituNavHost(
    navController: NavHostController = rememberNavController(),
    settingsManager: SettingsManager,
    startDestination: String = Login.route,
    viewModel1: ViewModel1,
    viewModel2: ViewModel2,
    viewModelSA: ScannerViewModel,
    viewModelMF: MalfunctionViewModel,
    switch: MutableState<String>,
    modifier: Modifier
){
    //Variáveis usadas para o controlo de conexão á internet da app
    val netstate by connectivityState()
    val isConnected = netstate === ConnectionState.Available

    //Inicio da UI
    //Coluna que contém o navegador ou NavHost
    Column {

        //NavHost é oq controla a navegação da app, nesta função contém todas as telas com a respetiva função para cada
        //É aqui que é ligado os objetos definidos na ficheiro HituDestination a cada ecrã
        NavHost(
            navController = navController,
            startDestination = startDestination
        ){
            composable(route = Login.route){
                LoginScreen(navController = navController, settingsManager = settingsManager)
            }
            composable(route = ScanProf.route){
                ScannerTeachScreen(navController = navController, viewModel = viewModelSA)
            }
            composable(ScanInput.route){
                ScannerInput(navController = navController, viewModel = viewModelSA)
            }
            composable(MalfList.route){
                MalfList(navController = navController, viewModel = viewModelMF)
            }
            composable(MalfInfo.route){
                MalfInfo(navController = navController, viewModel = viewModelMF)
            }
            composable(Create1.route){
                QrCreatePhase1(navController = navController, viewModel = viewModel1)
            }
            composable(Create2.route){
                QrCreatePhase2(navController = navController, viewModel = viewModel2)
            }
            composable(Create3.route){
                QrCreateFinal(navController = navController, viewModel1 = viewModel1, viewModel2 = viewModel2)
            }
            composable(Create3.route){
                QrCreateFinal(navController = navController, viewModel1 = viewModel1, viewModel2 = viewModel2)
            }
            composable(ChooseQr.route){
                ChooseQr(navController = navController, viewModel = viewModel1)
            }
            composable(TransferQr.route){
                TransferQr(navController = navController, viewModel = viewModel1)
            }
            composable(AdminChoices.route){
                AdminChoices(navController = navController)
            }
            composable(ScanAdmin.route){
                ScannerAdminScreen(navController = navController, viewModel = viewModelSA, settingsManager = settingsManager)
            }
            composable(ScannerAdminInfo.route){
                ScannerAdminInfo(navController = navController, viewModelSA)
            }
            composable(ScannerAdminInfoUpdate.route){
                ScannerAdminInfoUpdate(navController = navController, viewModelSA)
            }
            composable(SettingOptions.route){
                SettingsOptions(navController = navController, settingsManager = settingsManager, switch = switch)
            }
            composable(Manual.route){
                Manual(navController = navController, settingsManager = settingsManager)
            }
            composable(Loading.route){
                LoadingScreen(navController = navController, settingsManager = settingsManager)
            }
            composable(UserChoices.route){
                PrimaryChoice(navController = navController)
            }
            composable(MQRLocal.route){
                MQRLocal(navController = navController, viewModel = viewModelSA)
            }
            composable(RecentScanList.route){
                RecentScanList(navController = navController, settingsManager = settingsManager, viewModel = viewModelSA)
            }
            composable(MissingQrList.route){
                MissingQrList(navController = navController)
            }
            composable(TabScreen.route){
                TabLayout(navController = navController, viewModelSA = viewModelSA, settingsManager = settingsManager, viewModelMF = viewModelMF)
            }
            composable(ForgotPass.route){
                ForgotPass(navController = navController)
            }
            composable(About.route){
                About(navController = navController)
            }
            composable(WifiWarn.route){
                WifiWarn(navController = navController)
            }
        }
    }

    //Condição que verifica se a varíavel isConnected == fales
    if (!isConnected) {
        //Para evitar repetição temos esta condição que verifica se o utilizador já está ou não na tela de erro por falta de internet
        if (navController.currentDestination!!.route != WifiWarn.route) {
            //Navegação
            navController.navigate(WifiWarn.route)
        }
    }
    //Condição para que assim que o utilizador voltar a ter internet e ainda esteja na tela de erro voltar para trás
    if (navController.currentDestination!!.route == WifiWarn.route) {
        if (isConnected) {
            //Navegação
            navController.popBackStack()
        }
    }

}