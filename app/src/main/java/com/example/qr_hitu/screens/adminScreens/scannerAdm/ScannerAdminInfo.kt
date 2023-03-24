package com.example.qr_hitu.screens.adminScreens.scannerAdm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.functions.seeDispositivo
import androidx.compose.runtime.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun scannerAdminInfo(navController: NavController, viewModel: ScannerAdminViewModel){

    val (block, room, machine) = viewModel.myData.toString().split(",")
    val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.titleMedium
    var name = spec["Nome"]
    var processor = spec["Processador"]
    var ram = spec["Ram"]
    var powerSupply = spec["Fonte"]

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
            value = name,
            onValueChange = name,
            placeholder = { Text(text = "$name")},
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )


        Text("Ram: ")
        /*
        OutlinedTextField(
            value = newName,
            onValueChange = { newName },
            placeholder = { Text(name) },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        */

        Text("Fonte: ")
        /*
        OutlinedTextField(
            value = newName,
            onValueChange = { newName },
            placeholder = { Text(name) },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )
        */
    }
}