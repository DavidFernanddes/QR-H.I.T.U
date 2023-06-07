package com.example.qr_hitu.screens.adminScreens.transfer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.components.AdminChoices
import com.example.qr_hitu.functions.CreateQR
import com.example.qr_hitu.functions.Snackbar
import com.example.qr_hitu.functions.downloadQR
import com.example.qr_hitu.functions.encryptAES
import com.example.qr_hitu.functions.encryptionKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun TransferQr(navController: NavController, viewModel: ViewModel1) {

    var content by remember { mutableStateOf("") }
    var qrName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val showErr = remember { mutableStateOf(false) }
    val showDone = remember { mutableStateOf(false) }
    val enabled = qrName.isNotEmpty()

    val myData = viewModel.myData.value


    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Spacer(modifier = Modifier.padding(60.dp))

            if (myData != null) {
                content = encryptAES("${myData.block},${myData.room},${myData.machine}", encryptionKey)
            }

            val text = CreateQR(content = content)
            Image(bitmap = text, contentDescription = "qr", Modifier.size(200.dp))

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = qrName,
                onValueChange = {
                    qrName = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                label = { Text(text = stringResource(R.string.createNamePlaceholder)) },
                placeholder = { Text(text = "Nome") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                ),
            )

            Spacer(modifier = Modifier.height(30.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            downloadQR(text, qrName)
                            showDone.value = true
                        } catch (e: Exception) {
                            showErr.value = true
                        }
                    }
                },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                enabled = enabled
            ) {
                Text(
                    stringResource(R.string.createDownload),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        when {
            showDone.value -> {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Snackbar(text = stringResource(R.string.downloadStext))
                    LaunchedEffect(Unit) {
                        delay(2000)
                        showDone.value = false
                        navController.navigate(AdminChoices.route)
                    }
                }
            }

            showErr.value -> {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Snackbar(text = stringResource(R.string.downloadSEtext))
                    LaunchedEffect(Unit) {
                        delay(2000)
                        showErr.value = false
                        navController.navigate(AdminChoices.route)
                    }
                }
            }
        }
    }
}

