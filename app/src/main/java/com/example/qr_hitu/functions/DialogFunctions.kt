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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogReject() },
            title = {
                Text(
                    text = "Qr já existente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(text = "Deseja continuar a ação ?", style = MaterialTheme.typography.bodyMedium)
            },
            confirmButton= {
                TextButton(onClick = { openDialog.value = false; onDialogAccept(); }) {
                    Text(text = "Sim", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false; onDialogReject(); }) {
                    Text(text = "Não", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            textContentColor = MaterialTheme.colorScheme.onSecondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        )
    }
}

//Admin Scanner
@Composable
fun InvalidQrDialog(onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                androidx.compose.material.Text(
                    text = "Erro",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            text = {
                androidx.compose.material.Text(
                    text = "QR Code Inválido !",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            confirmButton= {
                TextButton(onClick = { openDialog.value = false;  onDialogDismissed()}) {
                    androidx.compose.material.Text(
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
}

//Missing Qr
@Composable
fun ExistsWarnDialog(onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Aviso já existente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Será colocado um novo QR assim que possível",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun AddMalfDialog(onDialogDismissed: () -> Unit, onDialogConfirm: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Adicionar",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Gostaria de colocar avaria ?",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; onDialogConfirm() }) {
                    Text(
                        text = "Sim",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                    Text(
                        text = "Não",
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

//User Scanner
@Composable
fun Malf_ErrorDialogs(onDialogDismissed: () -> Unit, navController: NavController, Err: Boolean ) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        if(!Err){
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissed() },
                title = {
                    Text(
                        text = "Avaria",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = "Deseja colocar uma avaria ?", style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false; navController.navigate(
                        ScanInput.route) }) {
                        Text(text = "SIM", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { openDialog.value = false;  onDialogDismissed()}) {
                        Text(text = "NÃO", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                    }
                },
                textContentColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary

            )
        }else{
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissed() },
                title = {
                    Text(
                        text = "Erro",
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(text = "QR Code Inválido !")
                },
                confirmButton= {
                    TextButton(onClick = { openDialog.value = false;  onDialogDismissed()}) {
                        Text(text = "Fechar")
                    }
                },
                textContentColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary

            )
        }
    }

}

//User Scanner Input
@Composable
fun WarningDialog(error: Boolean, onDialogDismissedError: () -> Unit, onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        if (error) {
            AlertDialog(
                onDismissRequest = { openDialog.value = false; onDialogDismissedError() },
                title = {
                    Text(
                        text = "Erro",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = "Descreva a avaria !", style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissedError() }) {
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
                onDismissRequest = { openDialog.value = false; onDialogDismissed() },
                title = {
                    Text(
                        text = "Sucesso",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall
                    )
                },
                text = {
                    Text(text = "Avaria Enviada !", style = MaterialTheme.typography.bodyMedium)
                },
                confirmButton = {
                    TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
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
}

@Composable
fun ExistsMalfDialog(onDialogDismissed: () -> Unit) {
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false; onDialogDismissed() },
            title = {
                Text(
                    text = "Avaria já existente",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                Text(
                    text = "Estamos a resolver a avaria o mais rápido possivel",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(onClick = { openDialog.value = false; onDialogDismissed() }) {
                    Text(
                        text = "OK",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            },
            textContentColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
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

//Overlays
@Composable
fun DelDialog(onDialogDismissed: () -> Unit, onDeleteClick: () -> Unit) {
    AlertDialog(
        onDismissRequest = { onDialogDismissed() },
        title = {
            Text(
                text = "Deseja apagar está máquina ?",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall
            )
        },
        text = {
            Text(text = "Esta ação é irreversível!", style = MaterialTheme.typography.bodyLarge)
        },
        dismissButton = {
            TextButton(onClick = { onDialogDismissed() }) {
                Text(
                    text = "Não",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onDialogDismissed(); onDeleteClick() }) {
                Text(
                    text = "Sim",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        textContentColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.primary
    )
}