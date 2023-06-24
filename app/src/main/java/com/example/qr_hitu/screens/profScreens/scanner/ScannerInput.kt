package com.example.qr_hitu.screens.profScreens.scanner

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.components.UserChoices
import com.example.qr_hitu.functions.WarningDialog
import com.example.qr_hitu.functions.DError_Success_Dialogs
import com.example.qr_hitu.functions.addMalfunction
import com.example.qr_hitu.functions.sendEmail
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
    val show = remember { mutableStateOf(false) }
    val show1 = remember { mutableStateOf(false) }
    val err = remember { mutableStateOf(false) }

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
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = stringResource(R.string.problem), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

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
                label = { Text(text = stringResource(R.string.problemChoice)) },
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

        Text(text = stringResource(R.string.problemDesc), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

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
                text = stringResource(R.string.urgent),
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
                                        show.value = true
                                        err.value = true
                                    }

                                    else -> {
                                        show.value = true
                                        sendEmail(email, block, room, machine, "uma avaria", "Avaria", malfunction, urgentState)
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
                                show.value = true
                                sendEmail(email, block, room, machine, "uma avaria", "Avaria", malfunction, urgentState)
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
                        show1.value = true
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
            Text(stringResource(R.string.problemSend), style = MaterialTheme.typography.labelLarge)
        }
        when {
            show.value -> DError_Success_Dialogs(
                error = err.value,
                onDialogDismissed = { show.value = false; navController.navigate(UserChoices.route) },
                onDialogDismissedError = { show.value = false; err.value = false; }
            )

            show1.value -> WarningDialog(
                onDialogDismissed = { show1.value = false; navController.navigate(UserChoices.route) },
                title = stringResource(R.string.existWMDtitle),
                text =  stringResource(R.string.existWMDtext)
            )
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