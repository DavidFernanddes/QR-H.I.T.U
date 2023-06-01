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
fun ScannerAdminInfoUpdate(navController: NavController, viewModel: ScannerViewModel) {

    val (block, room, machine) = viewModel.myData.value.toString().split(",")
    val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.titleMedium
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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(30.dp))

        Column() {
            Text("Bloco: $block ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Sala: $room ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Máquina: $machine", style = style)
        }

        Spacer(modifier = Modifier.padding(30.dp))

        Text("Especificações: $name", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = newProcessor,
            onValueChange = { newProcessor = it },
            label = { Text("Processador: $processor") },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = newRam,
            onValueChange = { newRam = it },
            label = { Text("Ram: $ram") },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = newPowerSupply,
            onValueChange = { newPowerSupply = it },
            label = { Text("Fonte: $powerSupply") },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                when{
                    newProcessor == "" -> newProcessor = "$processor"
                    newRam == "" -> newRam = "$ram"
                    newPowerSupply == "" -> newPowerSupply = "$powerSupply"
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
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = "Atualizar", style = MaterialTheme.typography.bodyLarge)
        }
    }
}