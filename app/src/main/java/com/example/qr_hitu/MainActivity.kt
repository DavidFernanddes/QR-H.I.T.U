package com.example.qr_hitu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.qr_hitu.screens.theme.QRHITUTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                DB()
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

@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    QRHITUTheme {
        DB()
    }
}
