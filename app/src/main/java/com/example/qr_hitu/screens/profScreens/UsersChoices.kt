package com.example.qr_hitu.screens.profScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.components.MQRLocal
import com.example.qr_hitu.components.ScanProf

@Composable
fun PrimaryChoice(navController: NavController) {

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        Arrangement.Center,
        Alignment.CenterVertically
    ) {
        Button(
            onClick = { navController.navigate(ScanProf.route) },
            modifier = Modifier
                .size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.PhotoCamera,
                    "Scan",
                    modifier = Modifier
                        .size(75.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    stringResource(R.string.scanner),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = { navController.navigate(MQRLocal.route) },
            modifier = Modifier
                .size(150.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            shape = MaterialTheme.shapes.medium
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Filled.QrCode ,
                    "Report",
                    modifier = Modifier
                        .size(75.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    stringResource(R.string.missingQR),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}