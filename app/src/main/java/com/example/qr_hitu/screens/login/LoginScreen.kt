package com.example.qr_hitu.screens.login

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.components.Loading
import com.example.qr_hitu.components.TabScreen
import com.example.qr_hitu.components.UserChoices
import com.example.qr_hitu.functions.Snackbar
import com.example.qr_hitu.functions.SettingsManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
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

fun loginVerify(navController: NavController, db: CollectionReference, email: String?, settingsManager: SettingsManager) {
    CoroutineScope(Dispatchers.Main).launch {
        delay(1500)
        // Perform the verification in the background using suspend functions
        val result = performVerification(db, email)

        // Navigate to the appropriate screen based on the verification result
        if (result.exists()) {
            settingsManager.saveSetting("Admin", "Admin")
            navController.navigate(TabScreen.route)
        } else {
            navController.navigate(UserChoices.route)
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    navController: NavController,
    settingsManager: SettingsManager,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {


    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val showSnack = remember { mutableStateOf(false) }
    val enabled = emailValue.isNotEmpty() && passwordValue.isNotEmpty()
    var showError by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val switch = remember { mutableStateOf("") }
    val theme by rememberUpdatedState(
        if (switch.value == "") {
            settingsManager.getSetting("Theme", "")
        } else switch.value
    )


    scope.launch {
        if (settingsManager.getSetting("AutoLogin", "false") == "true") {
            val db = Firebase.firestore.collection("Admin")
            val email = Firebase.auth.currentUser?.email
            delay(500)

            if (Firebase.auth.currentUser != null) {
                navController.navigate(Loading.route)
                loginVerify(navController, db, email, settingsManager)
            }
        }
    }


    Box (
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.padding(25.dp))

            when (theme) {
                "Light" -> Image(painterResource(R.drawable.logo_light), "Logo")
                "Dark" -> Image(painterResource(R.drawable.logo_dark), "Logo")
                else -> if (isDarkTheme) Image(
                    painterResource(R.drawable.logo_dark),
                    "Logo"
                ) else Image(painterResource(R.drawable.logo_light), "Logo")
            }

            Spacer(modifier = Modifier.padding(35.dp))

            Column {
                OutlinedTextField(
                    value = emailValue,
                    onValueChange = { emailValue = it },
                    label = { Text("Email") },
                    placeholder = { Text("Email") },
                    singleLine = true,
                    isError = showError,
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(onNext = {
                        focusManager.moveFocus(
                            FocusDirection.Down
                        )
                    }),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )

                Spacer(modifier = Modifier.padding(10.dp))

                OutlinedTextField(
                    value = passwordValue,
                    onValueChange = { passwordValue = it },
                    label = { Text(stringResource(R.string.password)) },
                    singleLine = true,
                    isError = showError,
                    placeholder = { Text(stringResource(R.string.password)) },
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
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                                    val email = Firebase.auth.currentUser?.email

                                    navController.navigate(Loading.route)
                                    loginVerify(navController, db, email, settingsManager)

                                } else {
                                    showError = true
                                    showSnack.value = true
                                }
                            }
                    },
                    Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    enabled = enabled
                ) {
                    Text(text = "Login", style = MaterialTheme.typography.labelLarge)
                }
            }
        }
        if (showSnack.value) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Snackbar(text = stringResource(R.string.loginStext))
                LaunchedEffect(Unit) {
                    delay(3000)
                    showSnack.value = false
                }
            }
        }
    }
}