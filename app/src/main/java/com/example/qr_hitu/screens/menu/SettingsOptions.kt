package com.example.qr_hitu.screens.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.theme.md_theme_light_primary
import com.example.qr_hitu.theme.md_theme_light_primaryContainer
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun SettingsOptions(navController: NavController, settingsManager: SettingsManager) {

    val showThemeState = remember { mutableStateOf(false) }
    val showTheme by rememberUpdatedState(showThemeState.value)
    val showLanguageState = remember { mutableStateOf(false) }
    val showLanguage by rememberUpdatedState(showLanguageState.value)

    val selectedTheme = remember { mutableStateOf("") }
    val selectedLanguage = remember { mutableStateOf("") }
    val selectedAutoLogin = remember { mutableStateOf(false) }
    val selectedBlockSession = remember { mutableStateOf(false) }

    selectedTheme.value = settingsManager.getSetting("Theme", "")
    selectedLanguage.value = settingsManager.getSetting("Language", "")
    selectedAutoLogin.value = settingsManager.getSetting("AutoLogin", "false").toBooleanStrict()
    selectedBlockSession.value = settingsManager.getSetting("BlockSession", "false").toBooleanStrict()

    val checkedTheme by rememberUpdatedState(selectedTheme.value)
    val checkedLanguage by rememberUpdatedState(selectedLanguage.value)
    val checkedAutoLogin = rememberUpdatedState(selectedAutoLogin.value)
    val checkedBlockSession = rememberUpdatedState(selectedBlockSession.value)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            "Aspeto",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness)

        Spacer(modifier = Modifier.padding(5.dp))

        TextButton(
            onClick = { showThemeState.value = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Tema", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.padding(5.dp))

                Text(checkedTheme.ifBlank { "Light" })
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        TextButton(
            onClick = { showLanguageState.value = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text("Idioma", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.padding(5.dp))

                Text(checkedLanguage.ifBlank { "Português" })
            }
        }

        Spacer(modifier = Modifier.padding(10.dp))

        Text(
            "Aspeto",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness)

        Spacer(modifier = Modifier.padding(5.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Auto Login",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 12.dp)
            )

            Switch(
                checked = checkedAutoLogin.value,
                onCheckedChange = {
                    selectedAutoLogin.value = !checkedAutoLogin.value
                    settingsManager.saveSetting("AutoLogin", (!checkedAutoLogin.value).toString())
                },
                enabled = true,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    checkedTrackColor = md_theme_light_primary
                )
            )

        }

        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Bloquear sessão",
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.padding(start = 12.dp)
            )

            Switch(
                checked = if (checkedAutoLogin.value) checkedBlockSession.value else false,
                onCheckedChange = {
                    selectedBlockSession.value = !checkedBlockSession.value
                    settingsManager.saveSetting("BlockSession", (!checkedBlockSession.value).toString())
                },
                enabled = checkedAutoLogin.value,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    checkedTrackColor = md_theme_light_primary
                )
            )

        }

        Spacer(modifier = Modifier.padding(10.dp))

        TextButton(
            onClick = {
                Firebase.auth.sendPasswordResetEmail(
                    Firebase.auth.currentUser?.email!!
                )
            },
            shape = MaterialTheme.shapes.small
        ) {
            Text("Alterar palavra-passe", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness)

        Spacer(modifier = Modifier.padding(5.dp))

        TextButton(
            onClick = {

            },
            shape = MaterialTheme.shapes.small
        ) {
            Text("Acerca de", style = MaterialTheme.typography.bodyLarge)
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness)

        Spacer(modifier = Modifier.padding(5.dp))

        TextButton(
            onClick = {

            },
            shape = MaterialTheme.shapes.small
        ) {
            Text("Sobre", style = MaterialTheme.typography.bodyLarge)
        }

        if (showTheme) {
            ThemeDialog(
                showThemeState,
                selectedTheme,
                settingsManager
            )
        }

        if (showLanguage) {
            LanguageDialog(
                showLanguageState,
                selectedLanguage,
                settingsManager
            )
        }
    }
}

@Composable
fun ThemeDialog(
    showThemeState: MutableState<Boolean>,
    selectedTheme: MutableState<String>,
    settingsManager: SettingsManager
) {
    AlertDialog(
        onDismissRequest = { showThemeState.value = false },
        title = {
            Text(
                text = "Escolha o tema",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme.value == "Light",
                        onClick = {
                            selectedTheme.value = "Light"
                            settingsManager.saveSetting("Theme", "Light")
                        }
                    )
                    Text("Claro")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme.value == "Dark",
                        onClick = {
                            selectedTheme.value = "Dark"
                            settingsManager.saveSetting("Theme", "Dark")
                        }
                    )
                    Text("Escuro")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme.value == "System",
                        onClick = {
                            selectedTheme.value = "System"
                            settingsManager.saveSetting("Theme", "System")
                        }
                    )
                    Text("Predefinido")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { showThemeState.value = false }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = md_theme_light_primary
                )
            }
        },
        textContentColor = md_theme_light_primaryContainer,
        titleContentColor = md_theme_light_primary
    )
}

@Composable
fun LanguageDialog(
    showLanguageState: MutableState<Boolean>,
    languageSelect: MutableState<String>,
    settingsManager: SettingsManager
) {
    AlertDialog(
        onDismissRequest = { showLanguageState.value = false },
        title = {
            Text(
                text = " Escolha idioma",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = languageSelect.value == "Português",
                        onClick = {
                            languageSelect.value = "Português"
                            settingsManager.saveSetting("Language", "Português")
                        }
                    )
                    Text("Português")
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = languageSelect.value == "Inglês",
                        onClick = {
                            languageSelect.value = "Inglês"
                            settingsManager.saveSetting("Language", "Inglês")
                        }
                    )
                    Text("Inglês")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { showLanguageState.value = false }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = md_theme_light_primary
                )
            }
        },
        textContentColor = md_theme_light_primaryContainer,
        titleContentColor = md_theme_light_primary
    )
}