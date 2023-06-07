package com.example.qr_hitu.functions

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.qr_hitu.R

//Create Qr Final
@Composable
fun DonwloadSnackbar(){
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.downloadStext), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ErrorSnackbar(){
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.downloadSEtext), style = MaterialTheme.typography.bodyMedium)
    }
}

//Login
@Composable
fun InvalidSnackbar() {
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = stringResource(R.string.loginStext), style = MaterialTheme.typography.bodyMedium)
    }
}

//Settings
@Composable
fun EmailSnackbar() {
    Snackbar(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(text = "Foi enviado um email para a troca de password", style = MaterialTheme.typography.bodyMedium)
    }
}