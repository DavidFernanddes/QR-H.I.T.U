package com.example.qr_hitu.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.functions.downloadQR
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


@Composable
fun ForgotPass(navController: NavController) {

    var email by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Filled.Lock,
            "lock",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(80.dp)
        )

        Text(text = stringResource(R.string.resetPass), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onSecondary)

        Spacer(modifier = Modifier.padding(10.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = { Text(text = "Email: ") },
            placeholder = { Text(text = "Email") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                focusedLabelColor = MaterialTheme.colorScheme.primaryContainer,
            )
        )

        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                Firebase.auth.sendPasswordResetEmail(email)
                navController.popBackStack()
            },
            Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            enabled = email.isNotEmpty(),
        ) {
            Text(
                stringResource(R.string.send),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}