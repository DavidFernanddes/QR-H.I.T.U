package com.example.qr_hitu.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.LoginScreen
import com.example.qr_hitu.screens.adminScreens.*
import com.example.qr_hitu.screens.userScreens.scannerInput
import com.example.qr_hitu.screens.userScreens.ScannerTeachScreen
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
            scannerInput(navController = navController)
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
            QrCreateFinal(navController = navController)
        }
        composable(ScanAdmin.route){
            ScannerAdminScreen(navController = navController)
        }
        composable(ScanAdminInfo.route){
           scannerAdminInfo(navController = navController)
        }
        composable(DefOptions.route){
            settingsOptions(navController = navController)
        }
        composable(Manual.route){
            manual(navController = navController)
        }

    }
}