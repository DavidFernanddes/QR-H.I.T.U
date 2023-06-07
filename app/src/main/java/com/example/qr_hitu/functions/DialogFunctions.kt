package com.example.qr_hitu.functions

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.components.ScanInput


//Create QR Phase 1
@Composable
fun ExistsInvDialog(onDialogAccept: () -> Unit, onDialogReject: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onDialogReject() },
            title = {
                Text(
                    text = stringResource(R.string.existDtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(text = stringResource(R.string.existDtext), style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton= {
                TextButton(onClick = { onDialogAccept(); }) {
                    Text(text = stringResource(R.string.confirm), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            dismissButton = {
                TextButton(onClick = { onDialogReject(); }) {
                    Text(text = stringResource(R.string.dismiss), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
}

//Missing Qr, User Scanner Input and //Admin Scanner
@Composable
fun WarningDialog(onDialogDismissed: () -> Unit, title: String, text: String) {
        AlertDialog(
            onDismissRequest = { onDialogDismissed() },
            title = {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { onDialogDismissed() }) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
}

@Composable
fun AddMalfDialog(onDialogDismissed: () -> Unit, onDialogConfirm: () -> Unit) {
        AlertDialog(
            onDismissRequest = { onDialogDismissed() },
            title = {
                Text(
                    text = stringResource(R.string.addMalfDtitle),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.addMalfDtext),
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { onDialogConfirm() }) {
                    Text(
                        text = stringResource(R.string.confirm),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { onDialogDismissed() }) {
                    Text(
                        text = stringResource(R.string.dismiss),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
}

//User Scanner
@Composable
fun Malf_ErrorDialogs(onDialogDismissed: () -> Unit, navController: NavController, Err: Boolean ) {
        if(!Err){
            AlertDialog(
                onDismissRequest = { onDialogDismissed() },
                title = {
                    Text(
                        text = stringResource(R.string.addMalfDtitle),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = stringResource(R.string.addMalfDtext), style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(
                        onClick = { navController.navigate(ScanInput.route) }) {
                        Text(text = stringResource(R.string.confirm), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { onDialogDismissed()}) {
                        Text(text = stringResource(R.string.dismiss), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                    }
                },
                textContentColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary

            )
        }else{
            AlertDialog(
                onDismissRequest = { onDialogDismissed() },
                title = {
                    Text(
                        text = stringResource(R.string.error),
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(text = stringResource(R.string.invalidDtext))
                },
                confirmButton= {
                    TextButton(onClick = { onDialogDismissed()}) {
                        Text(text = "OK")
                    }
                },
                textContentColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary

            )
        }
}

//User Scanner Input
@Composable
fun DError_Success_Dialogs(error: Boolean, onDialogDismissedError: () -> Unit, onDialogDismissed: () -> Unit) {

        if (error) {
            AlertDialog(
                onDismissRequest = { onDialogDismissedError() },
                title = {
                    Text(
                        text = stringResource(R.string.error),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = stringResource(R.string.descMalftext), style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(onClick = { onDialogDismissedError() }) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                textContentColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary
            )
        } else {
            AlertDialog(
                onDismissRequest = { onDialogDismissed() },
                title = {
                    Text(
                        text = stringResource(R.string.success),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = stringResource(R.string.descMalftext2), style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(onClick = { onDialogDismissed() }) {
                        Text(
                            text = "OK",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                textContentColor = MaterialTheme.colorScheme.onSecondary,
                titleContentColor = MaterialTheme.colorScheme.onSecondary
            )
        }
}

//Settings
@Composable
fun ThemeDialog(
    showThemeState: MutableState<Boolean>,
    selectedTheme: MutableState<String>,
    switch: MutableState<String>,
    settingsManager: SettingsManager
) {

    AlertDialog(
        onDismissRequest = { showThemeState.value = false },
        title = {
            Text(
                text = stringResource(R.string.themeDTitle),
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
                            switch.value = "Light"
                        }
                    )
                    Text(stringResource(R.string.themeL))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = selectedTheme.value == "Dark",
                        onClick = {
                            selectedTheme.value = "Dark"
                            settingsManager.saveSetting("Theme", "Dark")
                            switch.value = "Dark"
                        }
                    )
                    Text(stringResource(R.string.themeD))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { showThemeState.value = false }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        textContentColor = MaterialTheme.colorScheme.onSecondary,
        titleContentColor = MaterialTheme.colorScheme.onSecondary,
    )
}

@Composable
fun LanguageDialog(
    showLanguageState: MutableState<Boolean>,
    languageSelect: MutableState<String>,
    settingsManager: SettingsManager,
    context: Context
) {
    AlertDialog(
        onDismissRequest = { showLanguageState.value = false },
        title = {
            Text(
                text = stringResource(R.string.langDTitle),
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
                        selected = languageSelect.value == "pt",
                        onClick = {
                            languageSelect.value = "pt"
                            settingsManager.saveSetting("Language", "pt")
                            setLocale("pt", context)
                            recreateActivity(context)
                        }
                    )
                    Text(stringResource(R.string.langPT))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = languageSelect.value == "en",
                        onClick = {
                            languageSelect.value = "en"
                            settingsManager.saveSetting("Language", "en")
                            setLocale("en", context)
                            recreateActivity(context)
                        }
                    )
                    Text(stringResource(R.string.langEN))
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { showLanguageState.value = false }) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        textContentColor = MaterialTheme.colorScheme.onSecondary,
        titleContentColor = MaterialTheme.colorScheme.onSecondary
    )
}

//Overlays and //Missing Qr List
@Composable
fun DelDialog(onDialogDismissed: () -> Unit, onDeleteClick: () -> Unit, title: String, text: String) {
    AlertDialog(
        onDismissRequest = { onDialogDismissed() },
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(text = text, style = MaterialTheme.typography.bodyLarge)
        },
        dismissButton = {
            TextButton(onClick = { onDialogDismissed() }) {
                Text(
                    text = stringResource(R.string.dismiss),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDialogDismissed(); onDeleteClick() }) {
                Text(
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        },
        textContentColor = MaterialTheme.colorScheme.onSecondary,
        titleContentColor = MaterialTheme.colorScheme.onSecondary
    )
}