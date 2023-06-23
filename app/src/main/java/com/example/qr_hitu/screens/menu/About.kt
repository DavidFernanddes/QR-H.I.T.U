package com.example.qr_hitu.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R


@Composable
fun About(navController: NavController) {
    
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {

        Text(
            stringResource(R.string.appVersionT),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            stringResource(R.string.appVersion),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.padding(15.dp))

        Text(
            stringResource(R.string.infoT),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Text(
            stringResource(R.string.info),
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}