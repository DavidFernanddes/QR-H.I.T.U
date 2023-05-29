package com.example.qr_hitu.screens.adminScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.components.Create1
import com.example.qr_hitu.components.MalfList
import com.example.qr_hitu.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.theme.md_theme_light_primaryContainer

@Composable
fun AdminChoices(navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxSize(),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.navigate(MalfList.route) },
            modifier = Modifier
                .size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_primaryContainer,
                contentColor = md_theme_light_onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.FileDownload,
                    "Download",
                    modifier = Modifier
                        .size(75.dp),
                    tint = md_theme_light_onPrimaryContainer
                )
                Text(
                    "Transferir",
                    style = MaterialTheme.typography.titleMedium,
                    color = md_theme_light_onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = { navController.navigate(Create1.route) },
            modifier = Modifier
                .size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = md_theme_light_primaryContainer,
                contentColor = md_theme_light_onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.QrCode2,
                    "Create",
                    modifier = Modifier
                        .size(75.dp),
                    tint = md_theme_light_onPrimaryContainer
                )
                Text(
                    "Criar Qr",
                    style = MaterialTheme.typography.titleMedium,
                    color = md_theme_light_onPrimaryContainer
                )
            }
        }
    }
}