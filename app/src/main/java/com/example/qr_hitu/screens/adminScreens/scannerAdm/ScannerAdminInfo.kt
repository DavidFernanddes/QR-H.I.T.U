package com.example.qr_hitu.screens.adminScreens.scannerAdm

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.compose.runtime.*
import com.example.qr_hitu.ViewModels.ScannerViewModel


@Composable
fun scannerAdminInfo(navController: NavController, viewModel: ScannerViewModel){

    viewModel.myData.value?.let { Text(it) }
/*
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
    }*/
}