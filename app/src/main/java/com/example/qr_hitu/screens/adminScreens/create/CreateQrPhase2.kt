package com.example.qr_hitu.screens.adminScreens.create

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.qr_hitu.screens.components.Create3

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrCreatePhase2(navController : NavController, viewModel : ViewModel2){

    var name by remember { mutableStateOf("") }
    var processor by remember { mutableStateOf("") }
    var ram by remember { mutableStateOf("") }
    var powerSupply by remember { mutableStateOf("") }
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
        value = name,
        onValueChange = { name = it },
        label = { Text("Nome") },
        placeholder = { Text("Nome") },
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
        value = processor,
        onValueChange = { processor = it },
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
        value = powerSupply,
        onValueChange = { powerSupply = it },
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

    if (name.isNotEmpty() && processor.isNotEmpty() && ram.isNotEmpty() && powerSupply.isNotEmpty()) {
        Button(
            onClick = {
                viewModel.setMyData2(name, processor, ram, powerSupply)
                navController.navigate(Create3.route)
                      },
            Modifier.fillMaxWidth()
        ) {
            Text(text = "Continuar")
        }
    }
}

}