package com.example.qr_hitu.screens.adminScreens

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCreatePhase2(navController: NavController){

    var processador by remember { mutableStateOf("") }
    var ram by remember { mutableStateOf("") }
    var fonte by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        .padding(horizontal = 16.dp)
        .background(Color.White)
) {
    OutlinedTextField(
        value = processador,
        onValueChange = { processador = it },
        label = { Text("Processador") },
        placeholder = { Text("Processador") },
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
    )

    Spacer(modifier = Modifier.padding(10.dp))

    OutlinedTextField(
        value = ram,
        onValueChange = { ram = it },
        label = { Text("RAM") },
        placeholder = { Text("RAM") },
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
    )

    Spacer(modifier = Modifier.padding(10.dp))

    OutlinedTextField(
        value = fonte,
        onValueChange = { fonte = it },
        label = { Text("Fonte") },
        placeholder = { Text("Fonte") },
        singleLine = true,
        shape = MaterialTheme.shapes.large,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
    )

    Spacer(modifier = Modifier.padding(10.dp))

    if (processador.isNotEmpty() && ram.isNotEmpty() && fonte.isNotEmpty()) {
        Button(
            onClick = { navController.navigate("Create_QR_Final")},
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Continuar")
        }
    }
}

}