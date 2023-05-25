package com.example.qr_hitu.screens.adminScreens.create

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.components.Create2
import com.example.qr_hitu.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.theme.md_theme_light_primaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCreatePhase1(navController: NavController, viewModel : ViewModel1) {

    var textFiledSize by remember { mutableStateOf(Size.Zero) }

    var expanded3 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    val blocks = listOf("Bloco A", "Bloco B", "Bloco C", "Bloco D", "Bloco E")
    var rooms by remember { mutableStateOf(listOf<String>()) }
    val machines = listOf("Computador 1", "Computador 2", "Computador 3", "Computador 4", "Computador 5")

    var selectedBlock by remember { mutableStateOf("") }
    var selectedRoom by remember { mutableStateOf("") }
    var selectedMachine by remember { mutableStateOf("") }


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
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = "Escolha a localização", style = MaterialTheme.typography.titleMedium)

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
                label = { Text(text = "Escolha um bloco") },
                trailingIcon = {
                    Icon(icon, "", Modifier.clickable { expanded = !expanded })
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = md_theme_light_primaryContainer,
                    focusedLabelColor = md_theme_light_primaryContainer,
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

        if (selectedBlock.isNotEmpty()) {
            ExposedDropdownMenuBox(
                expanded = expanded2,
                onExpandedChange = {
                    expanded2 = !expanded2
                }
            ) {
                OutlinedTextField(
                    value = selectedRoom,
                    readOnly = true,
                    onValueChange = { selectedRoom = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Escolha uma sala") },
                    trailingIcon = {
                        Icon(icon2, "", Modifier.clickable { expanded2 = !expanded2 })
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = md_theme_light_primaryContainer,
                        focusedLabelColor = md_theme_light_primaryContainer,
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
                            onClick = { selectedRoom = room; expanded2 = false },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if(selectedRoom.isNotEmpty()){

            Text(text = "Escolha uma máquina", style = MaterialTheme.typography.titleMedium)

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
                    onValueChange = { selectedMachine = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                        .onGloballyPositioned { coordinates ->
                            textFiledSize = coordinates.size.toSize()
                        },
                    label = { Text(text = "Escolha uma máquina") },
                    trailingIcon = {
                        Icon(icon3, "", Modifier.clickable { expanded3 = !expanded3 })
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = md_theme_light_primaryContainer,
                        focusedLabelColor = md_theme_light_primaryContainer,
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
                            onClick = { selectedMachine = machine; expanded3 = false },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if(selectedMachine.isNotEmpty() ) {
            enabled = true
        }
            Button(
                onClick = {
                    viewModel.setMyData1(selectedBlock, selectedRoom, selectedMachine)
                    navController.navigate(Create2.route) },
                Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_primaryContainer,
                    contentColor = md_theme_light_onPrimaryContainer
                ),
                enabled = enabled
            ) {
                Text(text = "Continuar", style = MaterialTheme.typography.labelLarge)
            }
    }
}

