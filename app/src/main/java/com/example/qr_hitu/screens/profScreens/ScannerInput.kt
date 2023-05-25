package com.example.qr_hitu.screens.profScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.functions.addMalfunction
import com.example.qr_hitu.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.theme.md_theme_light_primary
import com.example.qr_hitu.theme.md_theme_light_primaryContainer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerInput(navController: NavController, viewModel: ScannerViewModel){

    val email = Firebase.auth.currentUser?.email.toString()
    var outro by remember { mutableStateOf("") }
    var malfunction by remember { mutableStateOf("") }
    var urgentState by remember { mutableStateOf(false) }
    val showState = remember { mutableStateOf(false) }
    val show by rememberUpdatedState(showState.value)
    val errState = remember { mutableStateOf(false) }
    val err by rememberUpdatedState(errState.value)
    val defaultOptions = listOf(
        "Computador não liga",
        "Computador liga mas não têm imagem",
        "...",
        "...",
        "Outro"
    )
    val (block, room, machine) = viewModel.myData.value.toString().split(",")

    var textFiledSize by remember { mutableStateOf(Size.Zero) }
    var expanded by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val icon = if (expanded) {
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

        Text(text = "Qual o problema ?", style = MaterialTheme.typography.titleMedium)
        
        Spacer(modifier = Modifier.padding(10.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = malfunction,
                readOnly = true,
                onValueChange = { malfunction = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
                    .onGloballyPositioned { coordinates ->
                        textFiledSize = coordinates.size.toSize()
                    },
                label = { Text(text = "Escolha qual o seu problema") },
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
                defaultOptions.forEach { defaultOption ->
                    DropdownMenuItem(
                        text = { Text(text = defaultOption) },
                        onClick = { expanded = false; malfunction = defaultOption },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if(malfunction == "Outro"){

            Text(text = "Descreva o problema", style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = outro,
                onValueChange = { outro = it },
                label = { Text("Outro") },
                placeholder = { Text("Outro") },
                singleLine = false,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = md_theme_light_primaryContainer,
                    focusedLabelColor = md_theme_light_primaryContainer,
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = urgentState,
                onClick = {
                    urgentState = !urgentState
                }
            )
            Text(
                text = "Urgente ?",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if(malfunction.isNotEmpty() || malfunction == "Outro" && outro.isNotEmpty()){

            Button(
                onClick = {
                    when (malfunction) {
                        "Outro" -> {
                            when (outro) {
                                "" -> {
                                    showState.value = true
                                    errState.value = true
                                }
                                else -> {
                                    showState.value = true
                                    addMalfunction(block,room,machine,outro, urgentState, email)
                                }
                            }
                        }
                        else -> {
                            showState.value = true
                            addMalfunction(block,room,machine,malfunction, urgentState, email)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_primaryContainer,
                    contentColor = md_theme_light_onPrimaryContainer
                )
            ) {
                Text("Enviar", style = MaterialTheme.typography.labelLarge)
            }
        }

        if(show) {
            Dialog(error = err, onDialogDismissed = { showState.value = false; errState.value = false })
        }

    }
}

@Composable
fun Dialog(error: Boolean, onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        if (error) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissed() },
                title = {
                    Text(
                        text = "Erro",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = "Descreva a avaria !", style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton= {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                        Text(text = "OK", style = MaterialTheme.typography.labelLarge, color = md_theme_light_primary)
                    }
                },
                textContentColor = md_theme_light_primaryContainer,
                titleContentColor = md_theme_light_primary
            )
        } else {
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissed() },
                title = {
                    Text(
                        text = "Sucesso",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = "Avaria Enviada !", style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton= {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                        Text(text = "OK", style = MaterialTheme.typography.labelLarge, color = md_theme_light_primary)
                    }
                },
                textContentColor = md_theme_light_primaryContainer,
                titleContentColor = md_theme_light_primary
            )
        }
    }
}
