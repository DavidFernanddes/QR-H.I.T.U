package com.example.qr_hitu.screens.profScreens.Scanner

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
import com.example.qr_hitu.components.UserChoices
import com.example.qr_hitu.functions.addMalfunction
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

fun retrieveDocumentsFromFirestore(completion: (List<String>) -> Unit) {
    // Firestore retrieval logic
    // For example:
    val firestore = Firebase.firestore
    val optionsCollection = firestore.collection("Padrões")

    optionsCollection.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val documents = task.result
            val optionsFromFirestore = documents?.mapNotNull { document ->
                document.id
            }

            completion(optionsFromFirestore.orEmpty())
        } else {
            // Handle the error case
            completion(emptyList())
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerInput(navController: NavController, viewModel: ScannerViewModel) {

    val email = Firebase.auth.currentUser?.email.toString()
    var outro by remember { mutableStateOf("") }
    var malfunction by remember { mutableStateOf("") }
    var urgentState by remember { mutableStateOf(false) }
    val showState = remember { mutableStateOf(false) }
    val show by rememberUpdatedState(showState.value)
    val showState1 = remember { mutableStateOf(false) }
    val show1 by rememberUpdatedState(showState1.value)
    val errState = remember { mutableStateOf(false) }
    val err by rememberUpdatedState(errState.value)

    var combinedOptions by remember { mutableStateOf(listOf<String>()) }

    retrieveDocumentsFromFirestore { optionsFromFirestore ->
        combinedOptions = optionsFromFirestore + "Outro"
    }

    val (block, room, machine) = viewModel.myData.value!!.split(",")

    var enabled2 by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(false) }

    enabled = malfunction == "Outro"
    enabled2 = malfunction.isNotEmpty() || malfunction == "Outro" && outro.isNotEmpty()

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

        Text(text = "Qual o problema ?", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

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
                combinedOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(text = option) },
                        onClick = { expanded = false; malfunction = option },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(text = "Descreva o problema", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = outro,
            onValueChange = { outro = it },
            enabled = enabled,
            label = { Text("Outro") },
            placeholder = { Text("Outro") },
            singleLine = false,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = urgentState,
                onClick = {
                    urgentState = !urgentState
                },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary
                )
            )
            Text(
                text = "Urgente ?",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                malfunctionExists(room, machine) { exists ->
                    if (!exists) {
                        when (malfunction) {
                            "Outro" -> {
                                when (outro) {
                                    "" -> {
                                        showState.value = true
                                        errState.value = true
                                    }

                                    else -> {
                                        showState.value = true
                                        addMalfunction(
                                            block,
                                            room,
                                            machine,
                                            outro,
                                            urgentState,
                                            email
                                        )
                                    }
                                }
                            }
                            else -> {
                                showState.value = true
                                addMalfunction(
                                    block,
                                    room,
                                    machine,
                                    malfunction,
                                    urgentState,
                                    email
                                )
                            }
                        }
                    } else {
                        showState1.value = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            enabled = enabled2
        ) {
            Text("Enviar", style = MaterialTheme.typography.labelLarge)
        }
        when {
            show -> Dialog(
                error = err,
                onDialogDismissed = { showState.value = false; navController.navigate(UserChoices.route) },
                onDialogDismissedError = { showState.value = false; errState.value = false; }
            )

            show1 -> ExistsDialog(onDialogDismissed = {
                showState1.value = false; navController.navigate(UserChoices.route)
            })
        }
    }
}

fun malfunctionExists(room: String, machine: String, onComplete: (Boolean) -> Unit) {
    val firestore = Firebase.firestore.collection("Avarias")

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
fun Dialog(error: Boolean, onDialogDismissedError: () -> Unit, onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        if (error) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissedError() },
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
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissedError() }) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                textContentColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary
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
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                        Text(
                            text = "OK",
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
}

@Composable
fun ExistsDialog(onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Avaria já existente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Estamos a resolver a avaria o mais rápido possivel",
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