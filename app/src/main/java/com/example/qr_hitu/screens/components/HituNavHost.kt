package com.example.qr_hitu.screens.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.LoginScreen


@Composable
fun qrHituNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "profile"
){
    NavHost(
        navController = navController, startDestination = "Login"
    ){
        /*
        composable("login"){
            LoginScreen(navController)
        }

         */
    }
}