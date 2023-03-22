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
import com.example.qr_hitu.screens.menu.Manual
import com.example.qr_hitu.screens.adminScreens.scannerAdm.ScannerAdminScreen
import com.example.qr_hitu.screens.adminScreens.scannerAdm.scannerAdminInfo
import com.example.qr_hitu.screens.menu.SettingsOptions
import com.example.qr_hitu.screens.profScreens.ScannerTeachScreen
import com.example.qr_hitu.screens.profScreens.ScannerInput
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun QrHituNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Login.route,
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
            QrCreatePhase1(navController = navController)
        }
        composable(Create2.route){
            QrCreatePhase2(navController = navController)
        }
        composable(Create3.route){
            QrCreateFinal(navController = navController, viewModel1 = QrCreate1ViewModel(), viewModel2 = QrCreate2ViewModel())
        }
        composable(ScanAdmin.route){
            ScannerAdminScreen(navController = navController)
        }
        composable(ScanAdminInfo.route){
           scannerAdminInfo(navController = navController)
        }
        composable(DefOptions.route){
            SettingsOptions(navController = navController)
        }
        composable(Manual.route){
            Manual(navController = navController)
        }

    }
}