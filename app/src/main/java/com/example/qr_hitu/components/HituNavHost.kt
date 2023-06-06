package com.example.qr_hitu.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.screens.LoadingScreen
import com.example.qr_hitu.screens.adminScreens.AdminChoices
import com.example.qr_hitu.screens.adminScreens.create.QrCreateFinal
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase1
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase2
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfInfo
import com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList.MalfList
import com.example.qr_hitu.screens.adminScreens.tabLists.recentScanList.RecentScanList
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminInfo
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminInfoUpdate
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminScreen
import com.example.qr_hitu.screens.adminScreens.tabLists.TabLayout
import com.example.qr_hitu.screens.adminScreens.tabLists.missingQrList.missingQrList
import com.example.qr_hitu.screens.adminScreens.transfer.ChooseQr
import com.example.qr_hitu.screens.adminScreens.transfer.TransferQr
import com.example.qr_hitu.screens.login.LoginScreen
import com.example.qr_hitu.screens.menu.Manual
import com.example.qr_hitu.screens.menu.SettingsOptions
import com.example.qr_hitu.screens.profScreens.missingQr.MQRLocal
import com.example.qr_hitu.screens.profScreens.PrimaryChoice
import com.example.qr_hitu.screens.profScreens.scanner.ScannerInput
import com.example.qr_hitu.screens.profScreens.scanner.ScannerTeachScreen


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
            Manual(navController = navController)
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
            missingQrList(navController = navController)
        }
        composable(TabScreen.route){
            TabLayout(navController = navController, viewModelSA = viewModelSA, settingsManager = settingsManager, viewModelMF = viewModelMF)
        }
    }
}