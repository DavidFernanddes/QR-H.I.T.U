package com.example.qr_hitu.screens.adminScreens.scannerAdm

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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.seeDispositivo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerAdminInfoUpdate(navController: NavController, viewModel: ScannerViewModel){

    val (block, room, machine) = viewModel.myData.toString().split(",")
    val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.bodyLarge
    val name = spec["Nome"]
    val processor = spec["Processador"]
    val ram = spec["Ram"]
    val powerSupply = spec["Fonte"]
    var newProcessor by remember { mutableStateOf("") }
    var newRam by remember { mutableStateOf("") }
    var newPowerSupply by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.padding(30.dp))

        Column() {
            Text("Bloco: $block ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Sala: $room ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Máquina: $machine", style = style)
        }

        Spacer(modifier = Modifier.padding(50.dp))

        Text("Especificações: $name", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Processador: $processor")

        OutlinedTextField(
            value = newProcessor,
            onValueChange = {newProcessor = it},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Ram: $ram")

        OutlinedTextField(
            value = newRam,
            onValueChange = {newRam = it},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Fonte: $powerSupply")

        OutlinedTextField(
            value = newPowerSupply,
            onValueChange = {newPowerSupply = it},
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(onClick = {
            if(newProcessor == null){
                newProcessor = "$processor"
            }
            if(newRam == null){
                newRam = "$ram"
            }
            if(newPowerSupply == null){
                newPowerSupply = "$powerSupply"
            }
            addDispositivo(
                block,
                room,
                machine,
                hashMapOf(
                    "Nome" to "$name",
                    "Processador" to newProcessor,
                    "Ram" to newRam,
                    "Fonte" to newPowerSupply
                )
            )
        }) {
            Text(text = "Login")
        }
    }
}