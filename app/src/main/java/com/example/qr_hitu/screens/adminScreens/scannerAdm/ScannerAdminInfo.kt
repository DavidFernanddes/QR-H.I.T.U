package com.example.qr_hitu.screens.adminScreens.scannerAdm

<<<<<<< Updated upstream
import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.compose.runtime.*
import com.example.qr_hitu.ViewModels.ScannerViewModel

=======
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.functions.seeDispositivo
>>>>>>> Stashed changes

@Composable
<<<<<<< Updated upstream
fun scannerAdminInfo(navController: NavController, viewModel: ScannerViewModel){
=======
fun ScannerAdminInfo(navController: NavController, viewModel: ScannerAdminViewModel){
>>>>>>> Stashed changes

    viewModel.myData.value?.let { Text(it) }
/*
    val (block, room, machine) = viewModel.myData.toString().split(",")
    val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.titleMedium
    val name = spec["Nome"]
    val processor = spec["Processador"]
    val ram = spec["Ram"]
    val powerSupply = spec["Fonte"]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {
        Row() {
            Text("Bloco: $block", style = style)
            Text("Sala: $room", style = style)
            Text("Máquina: $machine", style = style)
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Especificações: $name", style = style)

        Text("Processador: ")

        OutlinedTextField(
            value = "$processor",
            onValueChange = {},
            placeholder = { Text("$processor") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
        )


        Text("Ram: ")

        OutlinedTextField(
            value = "$ram",
            onValueChange = {},
            placeholder = { Text("$ram") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
        )

        Text("Fonte: ")

        OutlinedTextField(
            value = "$powerSupply",
            onValueChange = {},
            placeholder = { Text("$powerSupply") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
        )
<<<<<<< Updated upstream
        */
    }*/
=======
    }
>>>>>>> Stashed changes
}