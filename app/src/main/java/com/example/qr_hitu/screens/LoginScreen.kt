package com.example.qr_hitu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(){

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()){
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.White)){

            //TODO IMAGE LOGO
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(Color.White)
        ) {
            Text(
                text = "Sign In",
                style = TextStyle(
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.padding(28.dp))
            Column() {
                OutlinedTextField(
                    value = emailValue,
                    onValueChange = { emailValue = it},
                    label = { Text("Email")},
                    placeholder = { Text("Email")},
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(8.8f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    /*
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Cyan500,
                        focusedBorderColor = Cyan700,
                        focusedLabelColor = Gray200,
                    )

                     */
                    )

                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = { passwordValue = it },
                    label = { Text("Password") },
                    singleLine = true,
                    placeholder = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (passwordVisible) "Hide password" else "Show password"
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    /*
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Cyan500,
                        focusedBorderColor = Cyan700,
                        focusedLabelColor = Gray200
                        )

                     */
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {
    LoginPage()
    /*
    QRHITUTheme() {

    }

     */
}