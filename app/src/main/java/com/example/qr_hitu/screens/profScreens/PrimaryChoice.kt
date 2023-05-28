package com.example.qr_hitu.screens.profScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.screens.components.ScanProf
import com.example.qr_hitu.screens.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.screens.theme.md_theme_light_primary
import com.example.qr_hitu.screens.theme.md_theme_light_primaryContainer

@Composable
fun PrimaryChoice(navController: NavController) {



    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = { navController.navigate(ScanProf.route) },
            modifier = Modifier
                .size(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_primaryContainer,
                contentColor = md_theme_light_onPrimaryContainer
            )
        ) {
            Column {
                Icon(Icons.Filled.PhotoCamera , "Scan" )
                Text("Scanner")
            }
        }

        Spacer(modifier = Modifier.padding(30.dp))

        Button(
            onClick = { navController.navigate(ScanProf.route) },
            modifier = Modifier
                .size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_primaryContainer,
                contentColor = md_theme_light_onPrimaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .size(50.dp)
                    .background(color = md_theme_light_primary)
            ) {
                Icon(Icons.Filled.QrCode , "Report" )
                Text("Falta de QR")
            }
        }
    }
}