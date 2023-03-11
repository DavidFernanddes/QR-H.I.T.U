package com.example.qr_hitu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.delDispositivo
import com.example.qr_hitu.functions.seeDispositivo
import com.example.qr_hitu.screens.adminScreens.MalfList
import com.example.qr_hitu.screens.adminScreens.QrCreateFinal
import com.example.qr_hitu.screens.adminScreens.QrCreatePhase1
import com.example.qr_hitu.screens.adminScreens.QrCreatePhase2
import com.example.qr_hitu.screens.components.QrHituNavHost
import com.example.qr_hitu.screens.theme.QRHITUTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                QrCreateFinal()
                //QrHituNavHost()
                //DB()
            }
        }
    }
}

@Composable
fun DB () {

    val bloco = "Bloco E"
    val sala = "Sala E0.05"
    val ident = "Computador 1"
    var pc = hashMapOf(
        "Nome" to "Teste",
        "Processador" to "Intel",
        "Fonte" to "Cabo"
    )


    Column {

        seeDispositivo(bloco, sala, ident).forEach { (key, value) ->
            Text("$key: $value")
        }

        Button(onClick = { addDispositivo(bloco, sala, ident, pc) }) {
            Text("Add")
        }

        Button(onClick = { delDispositivo(bloco, sala, ident) }) {
            Text("Del")
        }

    }

}



fun CreateQrCode(modifier: Modifier = Modifier) {

    var content by remember { mutableStateOf("") }
    var qrName by remember { mutableStateOf("") }
    val context = LocalContext.current


    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        CreateQR(content)

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Escreva texto para conversão", color = Color(0xFFBB86FC)) },
            placeholder = { Text(text = "Texto a converter", color = Color.LightGray) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFBB86FC))

            )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = qrName,
            onValueChange = {
                qrName = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Escreva o nome do ficheiro", color = Color(0xFFBB86FC)) },
            placeholder = { Text(text = "Nome", color = Color.LightGray) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFBB86FC))

        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            DownloadQR(content, qrName, context)
        }) {
            Text("Download QR Code")
        }
    }

fun DefaultPreview() {
    QRHITUTheme {
        DB()
    }
}
