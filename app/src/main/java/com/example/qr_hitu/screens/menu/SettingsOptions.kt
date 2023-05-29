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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.theme.md_theme_light_primary
import com.example.qr_hitu.theme.md_theme_light_primaryContainer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsOptions(navController: NavController) {

    var checkedTheme by remember { mutableStateOf("Light") }
    var checkedLanguage by remember { mutableStateOf("Português") }

    val showThemeState = remember { mutableStateOf(false) }
    val showTheme by rememberUpdatedState(showThemeState.value)
    val showLanguageState = remember { mutableStateOf(false) }
    val showLanguage by rememberUpdatedState(showLanguageState.value)

    val lightThemeState = remember { mutableStateOf(false) }
    val lightTheme by rememberUpdatedState(lightThemeState.value)
    val darkThemeState = remember { mutableStateOf(false) }
    val darkTheme by rememberUpdatedState(darkThemeState.value)
    val systemThemeState = remember { mutableStateOf(false) }
    val systemTheme by rememberUpdatedState(systemThemeState.value)

    var languageSelectState by remember { mutableStateOf("") }
    val languageSelect by rememberUpdatedState(languageSelectState)

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

                Text("$checkedTheme")
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

                Text("$checkedLanguage")
            }
        }
        /*
        Switch(
            checked = checkedDarkMode,
            onCheckedChange = {
                checkedDarkMode = !checkedDarkMode
            },
            enabled = true,
            colors = SwitchDefaults.colors(
                uncheckedBorderColor = Color.Transparent,
                checkedTrackColor = md_theme_light_primary
            )
        )

         */

        if (showTheme) {
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
                                selected = lightTheme,
                                onClick = {
                                    lightThemeState.value = true
                                    darkThemeState.value = false
                                    systemThemeState.value = false
                                }
                            )
                            Text("Claro")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = darkTheme,
                                onClick = {
                                    lightThemeState.value = false
                                    darkThemeState.value = true
                                    systemThemeState.value = false
                                }
                            )
                            Text("Escuro")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = systemTheme,
                                onClick = {
                                    lightThemeState.value = false
                                    darkThemeState.value = false
                                    systemThemeState.value = true
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

        if (showLanguage) {
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
                                selected = languageSelect == "Português",
                                onClick = {
                                    languageSelectState = "Português"
                                }
                            )
                            Text("Português")
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = languageSelect == "Inglês",
                                onClick = {
                                    languageSelectState = "Inglês"
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
    }
}
