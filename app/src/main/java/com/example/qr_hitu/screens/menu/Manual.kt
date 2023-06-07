package com.example.qr_hitu.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.functions.SettingsManager


@Composable
fun Manual(navController: NavController, settingsManager: SettingsManager) {

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(
                enabled = true,
                state = rememberScrollState()
            )
    ) {
        Text(
            if (settingsManager.getSetting(
                    "Admin",
                    "User"
                ) == "Admin"
            ) stringResource(R.string.adminManual) else stringResource(R.string.userManual),
            color = MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Justify
        )
    }
}