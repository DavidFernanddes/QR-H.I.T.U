package com.example.qr_hitu.screens.adminScreens.malfunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.theme.md_theme_light_primaryContainer

@Composable
fun MalfInfo(navController: NavController, ) {

    //val (block, room, machine) = viewModel.myData.value.toString().split(",")
    //val spec = seeDispositivo(block, room, machine)
    val focusManager = LocalFocusManager.current
    val style = MaterialTheme.typography.bodyLarge

    val name = "damn"//spec["Nome"]
    val processor = "damn"//spec["Processador"]
    val ram = "damn"//spec["Ram"]
    val powerSupply = "damn"//spec["Fonte"]



    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .padding(horizontal = 16.dp)
            .background(Color.White)
    ) {

        Spacer(modifier = Modifier.padding(30.dp))

        Column() {
            Text("Bloco:  ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Sala:  ", style = style)
            Spacer(modifier = Modifier.padding(10.dp))
            Text("Máquina: ", style = style)
        }

        Spacer(modifier = Modifier.padding(30.dp))

        Text("Especificações: $name", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Processador: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$processor",
            onValueChange = {},
            placeholder = { Text("$processor") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(
                    FocusDirection.Down
                )
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Ram: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$ram",
            onValueChange = {},
            placeholder = { Text("$ram") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(
                    FocusDirection.Down
                )
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Text("Fonte: ", modifier = Modifier.align(Alignment.Start))

        OutlinedTextField(
            value = "$powerSupply",
            onValueChange = {},
            placeholder = { Text("$powerSupply") },
            singleLine = true,
            readOnly = true,
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(
                    FocusDirection.Down
                )
            }),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = md_theme_light_primaryContainer,
                focusedLabelColor = md_theme_light_primaryContainer,
            )
        )

    }

}
