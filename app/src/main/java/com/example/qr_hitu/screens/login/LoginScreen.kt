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
import com.example.qr_hitu.components.ForgotPass
import com.example.qr_hitu.components.Loading
import com.example.qr_hitu.functions.snackbar
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.loginVerify
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


//  Tela de Login
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    navController: NavController,
    settingsManager: SettingsManager,
    isDarkTheme: Boolean = isSystemInDarkTheme()
) {

    //  Estados para guardar o email e a password
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }
    //  Estado para verificar se tem de mostrar a palavra-passe ou não
    var passwordVisible by remember { mutableStateOf(false) }
    //  Mostra Snackbar
    val showSnack = remember { mutableStateOf(false) }
    //  Ativa componente se o email e password estiverem preenchidos
    val enabled = emailValue.isNotEmpty() && passwordValue.isNotEmpty()
    //  Mostra Snackbar de erro
    var showError by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    //  Coroutine
    val scope = rememberCoroutineScope()
    //  Variável de tema para trocar icon
    val switch = remember { mutableStateOf("") }
    val theme by rememberUpdatedState(
        if (switch.value == "") {
            settingsManager.getSetting("Theme", "")
        } else switch.value
    )

    //  Abre Coroutine
    scope.launch {
        //  Verifica se o utilizador tem a opção autoLogin ativa
        if (settingsManager.getSetting("AutoLogin", "false") == "true") {
            //  Vai buscar o email do utilizador atual
            val email = Firebase.auth.currentUser?.email
            //  Delay de 0.5 segundos
            delay(500)

            //  Caso exista utilizador atual
            if (Firebase.auth.currentUser != null) {
                //  Envia para a tela de Loading e verifica se é admin ou user
                navController.navigate(Loading.route)
                loginVerify(navController, email, settingsManager)
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

            //  Verifica o tema para mostrar qual logo mostrar
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

                Spacer(modifier = Modifier.padding(5.dp))

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
                        //  Mostra palavra-passe
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(imageVector = image, description)
                        }
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primaryContainer,
                        focusedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                )

                Spacer(modifier = Modifier.padding(5.dp))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        //  Envia para a tela de alteração de palavra-passe
                        navController.navigate(ForgotPass.route)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            stringResource(R.string.forgotPass),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Button(
                    onClick = {
                        //  Dá log in no utilizador
                        Firebase.auth.signInWithEmailAndPassword(emailValue, passwordValue)
                            .addOnCompleteListener { task ->
                                //  Caso esteja tudo certo passa para a verificação se é admin ou não
                                if (task.isSuccessful) {
                                    val email = Firebase.auth.currentUser?.email

                                    //  Envia o utilizador para a tela de Loading
                                    navController.navigate(Loading.route)
                                    loginVerify(navController, email, settingsManager)

                                } else {
                                    //  Caso dê erro mostra SnackBar de erro
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

        //  Condição para ver se tem de mostrar a Snackbar
        if (showSnack.value) {
            Box(contentAlignment = Alignment.BottomCenter) {
                snackbar(text = stringResource(R.string.loginStext))
                LaunchedEffect(Unit) {
                    //  Dá delay de 3 segundos e desliga a SnackBar
                    delay(3000)
                    showSnack.value = false
                }
            }
        }
    }
}