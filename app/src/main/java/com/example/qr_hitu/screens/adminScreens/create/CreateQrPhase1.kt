package com.example.qr_hitu.screens.adminScreens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ViewModel1
import com.example.qr_hitu.components.AdminChoices
import com.example.qr_hitu.components.Create2
import com.example.qr_hitu.functions.ExistsInvDialog
import com.example.qr_hitu.functions.qrExists


//  Tela inicial para criar QR Code
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCreatePhase1(navController: NavController, viewModel: ViewModel1) {

    //  Mostra Dialog
    val show  = remember { mutableStateOf(false) }

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    //  Se dropbox está expandida ou não
    var expanded3 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    //  Se o componente referido está ativo
    var enabled3 by remember { mutableStateOf(false) }
    var enabled2 by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    //  Lista com os blocos, salas e máquinas
    val blocks = listOf(/*"Bloco A", "Bloco B", "Bloco C", "Bloco D", */"Bloco E")
    var rooms by remember { mutableStateOf(listOf<String>()) }
    val machines =
        listOf("Computador 1", "Computador 2", "Computador 3", "Computador 4", "Computador 5")

    //  Bloco/Sala/Máquina selecionada
    var selectedBlock by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedMachine by remember { mutableStateOf("") }

    //  Ativa componente seguinte caso o anterior já esteja preenchido
    enabled = selectedBlock.isNotEmpty()
    enabled2 = selectedRoom.isNotEmpty()
    enabled3 = selectedMachine.isNotEmpty()

    //  Diz qual icon usar na dropbox dependendo se ele está expandida ou não
    val icon = if (expanded) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    val icon2 = if (expanded2) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }
    val icon3 = if (expanded3) {
        Icons.Filled.KeyboardArrowUp
    } else {
        Icons.Filled.KeyboardArrowDown
    }


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = stringResource(R.string.createLocal), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedBlock,
                readOnly = true,
                onValueChange = { selectedBlock = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onGloballyPositioned { coordinates ->
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text(text = stringResource(R.string.createBlock)) },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                blocks.forEach { block ->
                    DropdownMenuItem(
                        text = { Text(text = block) },
                        onClick = {
                            selectedBlock = block
                            rooms = listOf(
                                "Sala ${block.last()}0.03",
                                "Sala ${block.last()}0.04",
                                "Sala ${block.last()}0.05"
                            )
                            selectedRoom = ""
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }

        }

        Spacer(modifier = Modifier.padding(10.dp))

        ExposedDropdownMenuBox(
            expanded = expanded2,
            onExpandedChange = {
                expanded2 = !expanded2
            }
        ) {
            OutlinedTextField(
                value = selectedRoom,
                readOnly = true,
                enabled = enabled,
                onValueChange = { selectedRoom = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onGloballyPositioned { coordinates ->
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text(text = stringResource(R.string.createRoom)) },
                trailingIcon = {
                    Icon(icon2, "", Modifier.clickable { expanded2 = !expanded2 })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
            ExposedDropdownMenu(
                expanded = expanded2,
                onDismissRequest = {
                    expanded2 = false
                }
            ) {
                rooms.forEach { room ->
                    DropdownMenuItem(
                        text = { Text(text = room) },
                        enabled = enabled,
                        onClick = { selectedRoom = room; expanded2 = false },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = stringResource(R.string.createMachine), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        ExposedDropdownMenuBox(
            expanded = expanded3,
            onExpandedChange = {
                expanded3 = !expanded3
            }
        ) {
            OutlinedTextField(
                value = selectedMachine,
                readOnly = true,
                enabled = enabled2,
                onValueChange = { selectedMachine = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onGloballyPositioned { coordinates ->
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text(text = stringResource(R.string.createMachine)) },
                trailingIcon = {
                    Icon(icon3, "", Modifier.clickable { expanded3 = !expanded3 })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
            ExposedDropdownMenu(
                expanded = expanded3,
                onDismissRequest = {
                    expanded3 = false
                }
            ) {
                machines.forEach { machine ->
                    DropdownMenuItem(
                        text = { Text(text = machine) },
                        enabled = enabled2,
                        onClick = { selectedMachine = machine; expanded3 = false },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                //  Verifica se o QR Code existe
                qrExists(selectedBlock, selectedRoom, selectedMachine) { exists ->
                    if (!exists) {
                        //  Guarda as informações e passa para a próxima fase
                        viewModel.setMyData1(selectedBlock, selectedRoom, selectedMachine)
                        navController.navigate(Create2.route)
                    } else {
                        //  Mostra Dialog de erro
                        show.value = true
                    }
                }
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            enabled = enabled3
        ) {
            Text(text = stringResource(R.string.createContinue), style = MaterialTheme.typography.labelLarge)
        }

        //  Condição para mostrar Dialog
        if (show.value) {
            ExistsInvDialog(
                onDialogConfirm = { show.value = false; navController.navigate(Create2.route); viewModel.setMyData1(selectedBlock, selectedRoom, selectedMachine) },
                onDialogDismiss = { show.value = false; navController.navigate(AdminChoices.route) }
            )
        }
    }
}