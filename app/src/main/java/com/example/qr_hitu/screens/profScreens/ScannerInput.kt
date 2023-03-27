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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import com.example.qr_hitu.screens.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.screens.theme.md_theme_light_primaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScannerInput(navController: NavController){

    var outro by remember { mutableStateOf("") }
    var malfunction by remember { mutableStateOf("") }
    val defaultOptions = listOf(
        "Computador não liga",
        "Computador liga mas não têm imagem",
        "...",
        "...",
        "Outro"
    )

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
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Text(text = "Qual o problema ?", style = MaterialTheme.typography.titleLarge)
        
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
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = md_theme_light_primaryContainer,
                    focusedLabelColor = md_theme_light_primaryContainer
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
                        onClick = { malfunction = defaultOption },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        if(malfunction == "Outro"){

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
            )

        }

        if(malfunction.isNotEmpty() || malfunction == "Outro" && outro.isNotEmpty()){

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_primaryContainer,
                    contentColor = md_theme_light_onPrimaryContainer
                )
            ) {
                Text("Enviar", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}


