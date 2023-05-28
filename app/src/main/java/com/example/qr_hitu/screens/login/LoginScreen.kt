package com.example.qr_hitu.screens.login

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.screens.components.Loading
import com.example.qr_hitu.screens.components.MalfList
import com.example.qr_hitu.screens.components.PrimaryChoice
import com.example.qr_hitu.screens.components.ScanProf
import com.example.qr_hitu.screens.theme.md_theme_light_onPrimaryContainer
import com.example.qr_hitu.screens.theme.md_theme_light_primaryContainer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

suspend fun performVerification(db: CollectionReference, email: String?): DocumentSnapshot {
    return withContext(Dispatchers.IO) {
        return@withContext db.document("$email").get().await()
    }
}
fun loginVerify(navController: NavController, db: CollectionReference, email: String?){
    CoroutineScope(Dispatchers.Main).launch {
        delay(1500)
        // Perform the verification in the background using suspend functions
        val result = performVerification(db, email)

        // Navigate to the appropriate screen based on the verification result
        if (result.exists()) {
            navController.navigate(MalfList.route)
        } else {
            navController.navigate(PrimaryChoice.route)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, firestore: FirebaseFirestore) {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()


    scope.launch {
        val db = Firebase.firestore.collection("Admin")
        val email = Firebase.auth.currentUser?.email
        delay(500)

        if (Firebase.auth.currentUser != null) {
            navController.navigate(Loading.route)
            loginVerify(navController, db, email)
        }
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

        Spacer(modifier = Modifier.padding(20.dp))

        Text(
            text = "QR H.I.T.U",
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(modifier = Modifier.padding(25.dp))

        Image(painterResource(R.drawable.logo), "Logo")

        Spacer(modifier = Modifier.padding(35.dp))

        Column {
            OutlinedTextField(
                value = emailValue,
                onValueChange = { emailValue = it },
                label = { Text("Email") },
                placeholder = { Text("Email") },
                singleLine = true,
                shape = MaterialTheme.shapes.large,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = md_theme_light_primaryContainer,
                    focusedLabelColor = md_theme_light_primaryContainer
                )
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
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, description)
                    }
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = md_theme_light_primaryContainer,
                    focusedLabelColor = md_theme_light_primaryContainer
                )
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    Firebase.auth.signInWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Sign in success, update UI with the signed-in user's information
                                val db = Firebase.firestore.collection("Admin")
                                val uid = Firebase.auth.currentUser?.uid

                                navController.navigate(Loading.route)
                                loginVerify(navController, db, uid)

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.exception)
                            }
                        }
                },
                Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = md_theme_light_primaryContainer,
                    contentColor = md_theme_light_onPrimaryContainer
                )
            ) {
                Text(text = "Login", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}