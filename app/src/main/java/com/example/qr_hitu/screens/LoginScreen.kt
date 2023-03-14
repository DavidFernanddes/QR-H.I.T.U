package com.example.qr_hitu.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.screens.components.MalfList
import com.example.qr_hitu.screens.components.ScanProf
import com.example.qr_hitu.screens.theme.QRHITUTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, firestore: FirebaseFirestore,)   {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
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
        Text(
            text = "QR H.I.T.U",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.padding(28.dp))
        Column {
            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text("Email") },
                placeholder = { Text("Email") },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
            )

            Spacer(modifier = Modifier.padding(10.dp))

            OutlinedTextField(
                value = passwordValue,
                onValueChange = { passwordValue = it },
                label = { Text("Password") },
                singleLine = true,
                placeholder = { Text("Password") },
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                }
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                          val query = Firebase.firestore.collection("Utilizadores")
                              .whereEqualTo("Email", emailValue)
                              .whereEqualTo("Pass", passwordValue)


                    query.get().addOnSuccessListener { documents ->
                        val doc = documents.documents.firstOrNull()
                        if(doc != null){
                            val perm = doc.getLong("Perm")
                            when(perm){
                                0L -> navController.navigate(ScanProf.route)
                                1L -> navController.navigate(MalfList.route)
                                else -> errorMessage = "Permissão Inválida"
                            }
                        } else {
                            errorMessage = "Email ou Password inválida"
                        }
                    }.addOnFailureListener{
                        errorMessage = "Erro: ${it.message}"
                    }
                },
                Modifier.fillMaxWidth()
            ) {
                Text(text = "Login")
            }
        }
    }
}