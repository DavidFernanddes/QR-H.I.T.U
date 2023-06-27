package com.example.qr_hitu.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.components.About
import com.example.qr_hitu.functions.LanguageDialog
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.ThemeDialog
import com.example.qr_hitu.functions.snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay


//  Tela das Settings da aplicação
@Composable
fun SettingsOptions(navController: NavController, settingsManager: SettingsManager, switch: MutableState<String>) {

    //  Mostra SnackBar
    val showWarn = remember { mutableStateOf(false) }

    //  Mostrar Dialogs de tema e idioma
    val showTheme  = remember { mutableStateOf(false) }
    val showLanguage  = remember { mutableStateOf(false) }

    //  Qual opção está selecionada
    val selectedTheme = remember { mutableStateOf("") }
    val selectedLanguage = remember { mutableStateOf("") }
    val selectedAutoLogin = remember { mutableStateOf(false) }
    //val selectedBlockSession = remember { mutableStateOf(false) }

    //  Vai buscar os valores ás sharedPreferences
    selectedTheme.value = switch.value.ifBlank {
        settingsManager.getSetting("Theme", "").ifBlank {
            if (isSystemInDarkTheme()) "Dark"
            else "Light"
        }
    }
    selectedLanguage.value = settingsManager.getSetting("Language", "pt")
    selectedAutoLogin.value = settingsManager.getSetting("AutoLogin", "false").toBooleanStrict()
    //selectedBlockSession.value = settingsManager.getSetting("BlockSession", "false").toBooleanStrict()

    val context = LocalContext.current

    //  Estado para dar recompose na tela
    val (recompose, setRecompose) = remember{ mutableStateOf(false) }

    //  Caso o recompose não seja ativado
    if (!recompose) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    stringResource(R.string.look),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.padding(5.dp))

                TextButton(
                    //  Mostra Dialog de tema
                    onClick = { showTheme.value = true },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(R.string.theme),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            if (selectedTheme.value == "Light") stringResource(R.string.themeL) else stringResource(
                                R.string.themeD
                            ), color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Divider(
                    thickness = DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(5.dp))

                TextButton(
                    //  Mostra Dialog de idioma
                    onClick = { showLanguage.value = true },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(R.string.lang),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )

                        Spacer(modifier = Modifier.padding(5.dp))

                        Text(
                            if (selectedLanguage.value == "pt") stringResource(R.string.langPT) else stringResource(R.string.langEN),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    stringResource(R.string.security),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.padding(5.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        stringResource(R.string.autoLogin),
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(start = 12.dp),
                        color = MaterialTheme.colorScheme.onSecondary
                    )

                    Switch(
                        //  Ativa o auto login
                        checked = selectedAutoLogin.value,
                        onCheckedChange = {
                            selectedAutoLogin.value = !selectedAutoLogin.value
                            settingsManager.saveSetting(
                                "AutoLogin",
                                (!selectedAutoLogin.value).toString()
                            )
                        },
                        enabled = true,
                        colors = SwitchDefaults.colors(
                            uncheckedBorderColor = Color.Transparent,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            checkedThumbColor = Color.White
                        )
                    )
                }
                /*
                            Spacer(modifier = Modifier.padding(5.dp))

                            Divider(
                                thickness = DividerDefaults.Thickness,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(modifier = Modifier.padding(5.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    stringResource(R.string.lockSession),
                                    style = MaterialTheme.typography.labelLarge,
                                    modifier = Modifier.padding(start = 12.dp),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )

                                Switch(
                                    checked = if (checkedAutoLogin.value) checkedBlockSession.value else false,
                                    onCheckedChange = {
                                        selectedBlockSession.value = !checkedBlockSession.value
                                        settingsManager.saveSetting(
                                            "BlockSession",
                                            (!checkedBlockSession.value).toString()
                                        )
                                    },
                                    enabled = checkedAutoLogin.value,
                                    colors = SwitchDefaults.colors(
                                        uncheckedBorderColor = Color.Transparent,
                                        checkedTrackColor = MaterialTheme.colorScheme.primary,
                                        checkedThumbColor = Color.White
                                    )
                                )

                            }
                */
                Spacer(modifier = Modifier.padding(5.dp))

                Divider(
                    thickness = DividerDefaults.Thickness,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.padding(5.dp))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        //  Envia email para trocar palavra-passe e mostra Snackbar
                        Firebase.auth.sendPasswordResetEmail(
                            Firebase.auth.currentUser?.email!!
                        )
                        showWarn.value = true
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(R.string.changePass),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))

                Text(
                    stringResource(R.string.about),
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(start = 12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                Spacer(modifier = Modifier.padding(5.dp))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        //  Vai para a tela sobre nós
                        navController.navigate(About.route)
                    },
                    shape = MaterialTheme.shapes.small
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            stringResource(R.string.aboutApp),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                }
            }

            //  Condição para mostrar Dialogs ou Snackbar
            when {
                showTheme.value -> ThemeDialog(
                    showTheme,
                    selectedTheme,
                    switch,
                    settingsManager
                )

                showLanguage.value -> LanguageDialog(
                    showLanguage,
                    selectedLanguage,
                    settingsManager,
                    context,
                    setRecompose
                )

                showWarn.value -> {
                    Box(contentAlignment = Alignment.BottomCenter) {
                        snackbar(text = stringResource(R.string.resetPassStext))
                        LaunchedEffect(Unit) {
                            delay(3000)
                            showWarn.value = false
                        }
                    }
                }
            }
        }
    } else {
        //  Desligar recompose
        setRecompose(false)
    }
}