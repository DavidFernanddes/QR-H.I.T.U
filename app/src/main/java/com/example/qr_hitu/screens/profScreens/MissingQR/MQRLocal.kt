package com.example.qr_hitu.screens.profScreens.MissingQR

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.components.ScanInput
import com.example.qr_hitu.components.UserChoices
import com.example.qr_hitu.functions.addMissQR
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MQRLocal(navController: NavController, viewModel: ScannerViewModel) {

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    var expanded3 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    var enabled3 by remember { mutableStateOf(false) }
    var enabled2 by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    val showState = remember { mutableStateOf(false) }
    val show by rememberUpdatedState(showState.value)
    val showErrState = remember { mutableStateOf(false) }
    val showErr by rememberUpdatedState(showErrState.value)


    val blocks = listOf("Bloco A", "Bloco B", "Bloco C", "Bloco D", "Bloco E")
    var rooms by remember { mutableStateOf(listOf<String>()) }
    val machines =
        listOf("Computador 1", "Computador 2", "Computador 3", "Computador 4", "Computador 5")

    var selectedBlock by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedMachine by remember { mutableStateOf("") }

    enabled2 = selectedRoom.isNotEmpty()
    enabled = selectedBlock.isNotEmpty()
    enabled3 = selectedMachine.isNotEmpty()


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

        Text(text = stringResource(R.string.mQRLocal), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

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
                label = { Text(text = stringResource(R.string.mQRBlock)) },
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
                label = { Text(text = stringResource(R.string.mQRRoom)) },
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

        Text(text = stringResource(R.string.mQRMachine), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

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
                label = { Text(text = stringResource(R.string.mQRMachine)) },
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
            enabled = enabled3,
            onClick = {
                viewModel.setMyData(code = "$selectedBlock,$selectedRoom,$selectedMachine")
                missQrExists(selectedRoom, selectedMachine) { exists ->
                    if (exists) {
                        showErrState.value = true
                    } else {
                        addMissQR(selectedBlock, selectedRoom, selectedMachine)
                        showState.value = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = stringResource(R.string.mQRSend), style = MaterialTheme.typography.bodyLarge)
        }

        if (showErr) {
            ExistsDialog(onDialogDismissed = { showState.value = true })
        }

        if (show) {
            AddDialog(
                onDialogDismissed = {
                    showState.value = false; navController.navigate(UserChoices.route)
                },
                onDialogConfirm = {
                    showState.value = false; navController.navigate(ScanInput.route)
                }
            )
        }
    }
}

fun missQrExists(room: String, machine: String, onComplete: (Boolean) -> Unit) {
    val firestore = Firebase.firestore.collection("Falta QR")

    firestore.document("$room $machine")
        .get()
        .addOnSuccessListener { documentSnapshot ->
            val exists = documentSnapshot.exists()
            onComplete(exists)
        }
        .addOnFailureListener {
            onComplete(false)
        }
}

@Composable
fun AddDialog(onDialogDismissed: () -> Unit, onDialogConfirm: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Adicionar",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Gostaria de colocar avaria ?",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; onDialogConfirm() }) {
                    Text(
                        text = "Sim",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                    Text(
                        text = "Não",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun ExistsDialog(onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Aviso já existente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Será colocado um novo QR assim que possível",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    }
}