package com.example.qr_hitu.functions


import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.screens.components.QrHituNavHost
import com.example.qr_hitu.screens.theme.md_theme_light_primaryContainer




@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldLayouts(navController: NavController){

    Scaffold(
        topBar = { TopBar(navController = navController) },
        bottomBar = { BottomBar(navController = navController)}
    ) {

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController){

    when(navController.currentBackStackEntry?.destination?.route){
        "Malfunctions_List", "Scanner_Admin" -> {

            TopAppBar(
                title = { Text(text = "Admin") },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.Menu, "Menu")
                    }
                }
            )

        }
        "Create_QR_Phase1", "Create_QR_Phase2" -> {

            TopAppBar(
                title = { Text(text = "Admin") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack ,"Back")
                    }
                }
            )

        }
        "Create_QR_Final" ->{

            TopAppBar(
                title = { Text(text = "Admin") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("Malfunctions_List") }) {
                        Icon(Icons.Filled.Close ,"Close")
                    }
                }
            )

        }
        "Scanner_Input" ->{

        }

    }
}

@Composable
fun BottomBar(navController: NavController){

    when(navController.currentBackStackEntry?.destination?.route){
        "Malfunctions_List", "Scanner_Admin" ->{

            BottomAppBar() {
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "Malfunctions_List" ||
                            navController.currentBackStackEntry?.destination?.route == "Scanner_Admin",
                    label = { Text(text = "Avarias") },
                    onClick = { navController.navigate("Malfunctions_List") },
                    icon = {
                        Icon(Icons.Filled.FormatListBulleted, "Avarias")
                    }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "Scanner_Admin",
                    label = { Text(text = "Scanner") },
                    onClick = { navController.navigate("Scanner_Admin") },
                    icon = {
                        Icon(Icons.Filled.QrCodeScanner, "Scanner")
                    }
                )
                NavigationBarItem(
                    selected = navController.currentBackStackEntry?.destination?.route == "Create_QR_Phase1" ||
                            navController.currentBackStackEntry?.destination?.route == "Create_QR_Phase2" ||
                            navController.currentBackStackEntry?.destination?.route == "Create_QR_Final",
                    label = { Text(text = "Create") },
                    onClick = { navController.navigate("Create_QR_Phase1") },
                    icon = {
                        Icon(Icons.Filled.QrCode2, "Create")
                    }
                )
            }

        }
        "Create_QR_Phase1", "Create_QR_Phase2", "Create_QR_Final" ->{



        }

    }

}

@Composable
fun Row() {

}