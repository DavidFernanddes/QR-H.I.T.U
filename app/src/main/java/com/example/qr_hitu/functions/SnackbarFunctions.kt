package com.example.qr_hitu.functions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

//  Base de SnackBar usada

//  Create Qr Final/Transfer Qr, Login and Settings
@Composable
fun snackbar(text: String){
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.bodyMedium)
    }
}