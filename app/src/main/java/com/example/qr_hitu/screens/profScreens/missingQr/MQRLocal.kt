package com.example.qr_hitu.screens.profScreens.missingQr

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.components.ScanInput
import com.example.qr_hitu.components.UserChoices
import com.example.qr_hitu.functions.AddMalfDialog
import com.example.qr_hitu.functions.WarningDialog
import com.example.qr_hitu.functions.addMissQR
import com.example.qr_hitu.functions.SendEmail
import com.example.qr_hitu.functions.existentPcs
import com.example.qr_hitu.functions.missQrExists
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


//  Tela para escolher qual o dispositivo que falta o QR Code
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MQRLocal(navController: NavController, viewModel: ScannerViewModel) {

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    //  Estados para verificar se a dropbox está expandida
    var expanded3 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    //  Ativa os componentes
    var enabled3 by remember { mutableStateOf(false) }
    var enabled2 by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    //  Mostra Dialog
    val show = remember { mutableStateOf(false) }
    val showErr = remember { mutableStateOf(false) }

    //  Envia email de aviso aos admins
    val sendE = remember { mutableStateOf(false) }

    //  Informações para as dropboxes
    val blocks = listOf(/*"Bloco A", "Bloco B", "Bloco C", "Bloco D", */"Bloco E")
    var rooms by remember { mutableStateOf(listOf<String>()) }
    val (machines, setMachines) = remember { mutableStateOf(listOf<String>()) }

    //  Informação selecionada pelo utilizador
    var selectedBlock by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var room by remember { mutableStateOf("") }
    var selectedMachine by remember { mutableStateOf("") }

    //  Condições para ativar os componentes
    enabled = selectedBlock.isNotEmpty()
    if ((selectedRoom.isNotBlank() && machines.isEmpty()) || room != selectedRoom) {
        room = selectedRoom
        //  Função para ir á firestore buscar quais computadores aquela sala tem
        existentPcs(block = selectedBlock, room = selectedRoom, setMachines = setMachines)
        if (machines.isEmpty()) {
            enabled2 = true
        }
    }
    enabled3 = selectedMachine.isNotEmpty()


    //  Condição para verificar qual icon utilizar dependendo se a dropbox está aberta ou não
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
                //  Verifica se já existe aviso, caso não exista Mostra o Dialog de sucesso e envia email, caso exista mostra Dialog de erro
                missQrExists(selectedRoom, selectedMachine) { exists ->
                    if (exists) {
                        showErr.value = true
                    } else {
                        viewModel.setMyData(code = "$selectedBlock,$selectedRoom,$selectedMachine")
                        sendE.value = true
                        addMissQR(selectedBlock, selectedRoom, selectedMachine)
                        show.value = true
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

        //  Condição para enviar email
        if (sendE.value){
            SendEmail(Firebase.auth.currentUser?.email!!, selectedBlock, selectedRoom, selectedMachine, "a falta de um QR", "Falta QR", "", false)
            sendE.value = false
        }

        //  Mostra Dialog de erro
        if (showErr.value) {
            WarningDialog(
                //  Mostra Dialog de adicionar avaria
                onDialogDismissed = { show.value = true; showErr.value = false },
                title = stringResource(R.string.existWDtitle),
                text = stringResource(R.string.existWDtext)
            )
        }

        //  Mostra Dialog de adicionar avaria
        if (show.value) {
            AddMalfDialog(
                onDialogDismissed = {
                    //  Envia para a tela de escolha
                    show.value = false; navController.navigate(UserChoices.route)
                },
                onDialogConfirm = {
                    //  Envia para a tela de avaria
                    show.value = false; navController.navigate(ScanInput.route)
                }
            )
        }
    }
}