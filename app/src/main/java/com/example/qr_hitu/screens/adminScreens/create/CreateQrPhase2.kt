package com.example.qr_hitu.screens.adminScreens.create

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
import com.example.qr_hitu.components.Create3
import com.example.qr_hitu.ViewModels.ViewModel2

@Composable
fun QrCreatePhase2(navController: NavController, viewModel: ViewModel2) {

    var name by remember { mutableStateOf("") }
    var processor by remember { mutableStateOf("") }
    var ram by remember { mutableStateOf("") }
    var powerSupply by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    var enabled by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(20.dp))

        Text(text = stringResource(R.string.createSpecs), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(stringResource(R.string.createName)) },
            placeholder = { Text(stringResource(R.string.createName)) },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = processor,
            onValueChange = { processor = it },
            label = { Text(stringResource(R.string.createProcessor)) },
            placeholder = { Text(stringResource(R.string.createProcessor)) },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = ram,
            onValueChange = { ram = it },
            label = { Text(stringResource(R.string.createRam)) },
            placeholder = { Text(stringResource(R.string.createRam)) },
            singleLine = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = powerSupply,
            onValueChange = { powerSupply = it },
            label = { Text(stringResource(R.string.createPower)) },
            placeholder = { Text(stringResource(R.string.createPower)) },
            singleLine = true,
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

        if (name.isNotEmpty() && processor.isNotEmpty() && ram.isNotEmpty() && powerSupply.isNotEmpty()) {
            enabled = true
        }
            Button(
                onClick = {
                    viewModel.setMyData2(name, processor, ram, powerSupply)
                    navController.navigate(Create3.route)
                },
                Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                enabled = enabled
            ) {
                Text(text = stringResource(R.string.createContinue), style = MaterialTheme.typography.labelLarge)
            }
    }
}