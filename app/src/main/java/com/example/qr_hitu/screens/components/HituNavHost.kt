package com.example.qr_hitu.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.adminScreens.create.*
import com.example.qr_hitu.screens.login.LoginScreen
import com.example.qr_hitu.screens.adminScreens.malfList.MalfList
import com.example.qr_hitu.screens.adminScreens.create.QrCreateFinal
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase1
import com.example.qr_hitu.screens.adminScreens.create.QrCreatePhase2
import com.example.qr_hitu.screens.menu.Manual
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminScreen
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminViewModel
import com.example.qr_hitu.screens.adminScreens.scannerAdm.scannerAdminInfo
import com.example.qr_hitu.screens.menu.SettingsOptions
import com.example.qr_hitu.screens.profScreens.ScannerTeachScreen
import com.example.qr_hitu.screens.profScreens.ScannerInput
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun QrHituNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Login.route,
    viewModel1: ViewModel1,
    viewModel2: ViewModel2,
    viewModelSA: ScannerAdminViewModel,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Login.route){
            LoginScreen(navController = navController, firestore = FirebaseFirestore.getInstance())
        }
        composable(route = ScanProf.route){
            ScannerTeachScreen(navController = navController)
        }
        composable(ScanInput.route){
            ScannerInput(navController = navController)
        }
        composable(MalfList.route){
            MalfList(navController = navController)
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
        composable(ScanAdmin.route){
            ScannerAdminScreen(navController = navController, viewModel = viewModelSA)
        }
        composable(ScanAdminInfo.route){
           scannerAdminInfo(navController = navController, viewModelSA)
        }
        composable(SettingOptions.route){
            SettingsOptions(navController = navController)
        }
        composable(Manual.route){
            Manual(navController = navController)
        }

    }
}