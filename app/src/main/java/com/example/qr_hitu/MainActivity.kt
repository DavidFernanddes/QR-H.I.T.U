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
import com.example.qr_hitu.screens.adminScreens.create.ViewModel2
import com.example.qr_hitu.screens.components.MalfList
import com.example.qr_hitu.screens.components.ScanProf
import com.example.qr_hitu.screens.theme.QRHITUTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {

                val navController = rememberNavController()
                val viewModel1 = viewModel<ViewModel1>()
                val viewModel2 = viewModel<ViewModel2>()

                ScaffoldLayouts(navController = navController, viewModel1 = viewModel1, viewModel2 = viewModel2)
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