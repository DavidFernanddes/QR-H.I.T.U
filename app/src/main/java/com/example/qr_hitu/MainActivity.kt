package com.example.qr_hitu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.delDispositivo
import com.example.qr_hitu.functions.seeDispositivo
import com.example.qr_hitu.screens.adminScreens.QrCreateFinal
import com.example.qr_hitu.screens.components.QrHituNavHost
import com.example.qr_hitu.screens.theme.QRHITUTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                QrHituNavHost()
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