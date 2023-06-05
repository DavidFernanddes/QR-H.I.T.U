package com.example.qr_hitu.screens.adminScreens.malfunctions

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.functions.seeDispositivo
import com.example.qr_hitu.functions.seeMalfunction

@Composable
fun MalfInfo(navController: NavController, viewModel: MalfunctionViewModel) {

    val style = MaterialTheme.typography.titleMedium
    var completeState by remember { mutableStateOf(false) }

    val room = viewModel.myData.value!!.room
    val urgent = viewModel.myData.value!!.urgent
    val machine = viewModel.myData.value!!.name
    val block = viewModel.myData.value!!.block

    val malf = seeMalfunction(machine, room)
    val malfDesc = malf["Avaria"]
    val senderMail = malf["Email"]

    val spec = seeDispositivo(block, room, machine)
    val processor = spec["Processador"]
    val ram = spec["Ram"]
    val powerSupply = spec["Fonte"]
    val scrollState = rememberScrollState()


    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .scrollable(state = scrollState, orientation = Orientation.Vertical)
            .padding(horizontal = 16.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.padding(10.dp))

            Column(horizontalAlignment = Alignment.Start) {
                Text( stringResource(R.string.MInfSender)+" "+senderMail, style = style, color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(stringResource(R.string.MInfBlock)+" "+block, style = style, color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(stringResource(R.string.MInfRoom)+" "+room, style = style, color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.padding(10.dp))
                Text(stringResource(R.string.MInfMachine)+" "+machine, style = style, color = MaterialTheme.colorScheme.onSecondary)
                Spacer(modifier = Modifier.padding(10.dp))
                if(urgent) Row{
                    Text(text = stringResource(R.string.MInfUrgent)+" ", style = style, color = MaterialTheme.colorScheme.onSecondary)
                    Icon(Icons.Filled.Error, "Urgent", tint = Color.Red)
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            OutlinedTextField(
                value = "$malfDesc",
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.MInfMalf)) },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(stringResource(R.string.MInfSpecs)+" "+machine, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSecondary)

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = "$processor",
                onValueChange = {},
                label = { Text(stringResource(R.string.MInfProcessor)) },
                singleLine = true,
                readOnly = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = "$ram",
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                label = { Text(stringResource(R.string.MInfRam)) },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = "$powerSupply",
                onValueChange = {},
                singleLine = true,
                readOnly = true,
                label = { Text(stringResource(R.string.MInfPower)) },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                    focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
                )
            )
        }
            Spacer(modifier = Modifier.padding(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = completeState,
                    onClick = {
                        completeState = !completeState
                        //TODO REMOVE DATABASE AND CHANGE TO ANOTHER
                        //Temporary function
                    },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = MaterialTheme.colorScheme.primary
                    )
                )
                Text(
                    text = stringResource(R.string.MInfSend),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
    }

}
