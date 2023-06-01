package com.example.qr_hitu.screens.adminScreens.transfer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.functions.CreateQR
import com.example.qr_hitu.functions.downloadQR
import com.example.qr_hitu.functions.encryptAES
import com.example.qr_hitu.functions.encryptionKey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransferQr(navController: NavController, viewModel : ViewModel1){

    var content by remember{ mutableStateOf("") }
    var qrName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val myData = viewModel.myData.value


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)

    ){

        Spacer(modifier = Modifier.padding(60.dp))

        if (myData != null) {
            content = encryptAES("${myData.block},${myData.room},${myData.machine}", encryptionKey)
        }

        CreateQR(content)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = qrName,
            onValueChange = {
                qrName = it
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = { Text(text = "Escreva o nome do ficheiro") },
            placeholder = { Text(text = "Nome") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(onClick = { downloadQR(content, qrName) },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text("Download QR Code", style = MaterialTheme.typography.labelLarge)
        }
    }

}