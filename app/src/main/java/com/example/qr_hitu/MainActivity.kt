package com.example.qr_hitu


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.qr_hitu.functions.ScaffoldLayouts
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.delDispositivo
import com.example.qr_hitu.functions.seeDispositivo
import com.example.qr_hitu.screens.adminScreens.create.ViewModel1
import com.example.qr_hitu.screens.theme.QRHITUTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                val navController = rememberNavController()
                val viewModel = viewModel<ViewModel1>()
                ScaffoldLayouts(navController = navController, viewModel = viewModel)
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