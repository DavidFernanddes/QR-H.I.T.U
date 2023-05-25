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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.seeDispositivo
import com.example.qr_hitu.theme.md_theme_light_primaryContainer


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerAdminInfo(navController: NavController, viewModel: ScannerViewModel){

    val (block, room, machine) = viewModel.myData.value.toString().split(",")
    val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.bodyLarge
    val name = spec["Nome"]
    val processor = spec["Processador"]
    val ram = spec["Ram"]
    val powerSupply = spec["Fonte"]



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

        Spacer(modifier = Modifier.padding(40.dp))

        Text("Especificações: $name", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Processador: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$processor",
            onValueChange = {},
            placeholder = { Text("$processor") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Ram: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$ram",
            onValueChange = {},
            placeholder = { Text("$ram") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Fonte: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$powerSupply",
            onValueChange = {},
            placeholder = { Text("$powerSupply") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

    }

}