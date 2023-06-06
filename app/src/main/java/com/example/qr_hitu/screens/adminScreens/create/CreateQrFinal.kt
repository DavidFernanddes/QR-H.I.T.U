package com.example.qr_hitu.screens.adminScreens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.ViewModels.ViewModel2
import com.example.qr_hitu.components.AdminChoices
import com.example.qr_hitu.functions.CreateQR
import com.example.qr_hitu.functions.DonwloadSnackbar
import com.example.qr_hitu.functions.ErrorSnackbar
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.downloadQR
import com.example.qr_hitu.functions.encryptAES
import com.example.qr_hitu.functions.encryptionKey
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QrCreateFinal(navController: NavController, viewModel1: ViewModel1, viewModel2: ViewModel2) {

    var content by remember { mutableStateOf("") }
    var qrName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val showErr = remember { mutableStateOf(false) }
    val showDone = remember { mutableStateOf(false) }

    val myData = viewModel1.myData.value
    val myData2 = viewModel2.myData.value
    val spec = hashMapOf(
        "Nome" to "${myData2?.name}",
        "Processador" to "${myData2?.processor}",
        "Ram" to "${myData2?.ram}",
        "Fonte" to "${myData2?.powerSupply}"
    )


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(60.dp))

        if (myData != null) {
            content = encryptAES("${myData.block},${myData.room},${myData.machine}", encryptionKey)
            addDispositivo(myData.block, myData.room, myData.machine, spec)
        }

        CreateQR(content)

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
            placeholder = { Text(text = stringResource(R.string.createName)) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        downloadQR(content, qrName)
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
            )
        ) {
            Text(
                stringResource(R.string.createDownload),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
    when {
        showDone.value -> {
            DonwloadSnackbar()
            LaunchedEffect(Unit) {
                delay(3000)
                showDone.value = false
                navController.navigate(AdminChoices.route)
            }
        }
        showErr.value -> {
            ErrorSnackbar()
            LaunchedEffect(Unit) {
                delay(3000)
                showErr.value = false
                navController.navigate(AdminChoices.route)
            }
        }
    }
}