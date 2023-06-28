package com.example.qr_hitu.screens.adminScreens.scannerAdm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.addDispositivo
import com.example.qr_hitu.functions.seeDispositivo


//  Tela para atualizar as informações de respetivo QR
@Composable
fun ScannerAdminInfoUpdate(navController: NavController, viewModel: ScannerViewModel) {

    //  Informações vindas do QR Code
    val (block, room, machine) = viewModel.myData.value.toString().split(",")
    //  Vai buscar as especificações
    val spec = seeDispositivo(block, room, machine)

    val focusManager = LocalFocusManager.current

    val style = MaterialTheme.typography.titleMedium
    //  Variáveis das especificações
    val name = spec["Nome"]
    val processor = spec["Processador"]
    val ram = spec["Ram"]
    val powerSupply = spec["Fonte"]
    //  Estados que guardam oq a pessoa escrever
    var newName by remember { mutableStateOf("") }
    var newProcessor by remember { mutableStateOf("") }
    var newRam by remember { mutableStateOf("") }
    var newPowerSupply by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(20.dp))

        Column {
            Text(stringResource(R.string.MInfBlock) +" "+block, style = style, color = MaterialTheme.colorScheme.onSecondary)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(stringResource(R.string.MInfRoom)+" "+room, style = style, color = MaterialTheme.colorScheme.onSecondary)
            Spacer(modifier = Modifier.padding(10.dp))
            Text(stringResource(R.string.MInfMachine)+" "+machine, style = style, color = MaterialTheme.colorScheme.onSecondary)
        }

        Spacer(modifier = Modifier.padding(20.dp))

        Text(stringResource(R.string.MInfSpecs), style = style, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = newName,
            onValueChange = { newName = it },
            label = { Text(stringResource(R.string.ScanInfoMN)+" "+name) },
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
            value = newProcessor,
            onValueChange = { newProcessor = it },
            label = { Text(stringResource(R.string.MInfProcessor)+" "+processor) },
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
            label = { Text(stringResource(R.string.MInfRam)+" "+ram) },
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
            label = { Text(stringResource(R.string.MInfPower)+" "+powerSupply) },
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
                //  Condição para verificar se o utilizador alterou algum campo
                //  Caso não vai a informação antiga
                newName = newName.ifBlank { name.toString() }
                newProcessor = newProcessor.ifBlank { processor.toString() }
                newRam = newRam.ifBlank { ram.toString() }
                newPowerSupply = newPowerSupply.ifBlank { powerSupply.toString() }
                //  Adiciona o dispositivo "novo"
                addDispositivo(
                    block,
                    room,
                    machine,
                    hashMapOf(
                        "Nome" to newName,
                        "Processador" to newProcessor,
                        "Ram" to newRam,
                        "Fonte" to newPowerSupply
                    )
                )
                //  Volta para trás
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
            Text(text = stringResource(R.string.ScanInfoUpd), style = MaterialTheme.typography.bodyLarge)
        }
    }
}