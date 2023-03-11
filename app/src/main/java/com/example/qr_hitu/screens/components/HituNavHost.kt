package com.example.qr_hitu.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.LoginScreen
import com.example.qr_hitu.screens.adminScreens.MalfList
import com.example.qr_hitu.screens.adminScreens.QrCreatePhase1
import com.example.qr_hitu.screens.adminScreens.QrCreatePhase2
import com.example.qr_hitu.screens.userScreens.scannerInput
import com.example.qr_hitu.screens.userScreens.ScannerTeachScreen
import com.google.firebase.firestore.FirebaseFirestore


@Composable
fun QrHituNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "Login"
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){

        composable("Login"){
            LoginScreen(navController = navController, firestore = FirebaseFirestore.getInstance())
        }
        composable("Scanner_Prof"){
            ScannerTeachScreen(navController = navController)
        }
        composable("Scanner_Input"){
            scannerInput(navController = navController)
        }
        composable("Malfunctions_List"){
            MalfList(navController = navController)
        }
        composable("Create_QR_Phase1"){
            QrCreatePhase1(navController = navController)
        }
        composable("Create_Qr_Phase2"){
            QrCreatePhase2(navController = navController)
        }
        composable("Create_Qr_Final"){

        }

    }
}