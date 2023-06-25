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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
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
import com.example.qr_hitu.functions.Snackbar
import com.example.qr_hitu.functions.ThemeDialog
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay

@Composable
fun SettingsOptions(navController: NavController, settingsManager: SettingsManager, switch: MutableState<String>) {

    val showWarn = remember { mutableStateOf(false) }

    val showTheme  = remember { mutableStateOf(false) }
    val showLanguage  = remember { mutableStateOf(false) }

    val selectedTheme = remember { mutableStateOf("") }
    val selectedLanguage = remember { mutableStateOf("") }
    val selectedAutoLogin = remember { mutableStateOf(false) }
    val selectedBlockSession = remember { mutableStateOf(false) }

    selectedTheme.value = switch.value.ifBlank {
        settingsManager.getSetting("Theme", "").ifBlank {
            if (isSystemInDarkTheme()) "Dark"
            else "Light"
        }
    }
    selectedLanguage.value = settingsManager.getSetting("Language", "pt")
    selectedAutoLogin.value = settingsManager.getSetting("AutoLogin", "false").toBooleanStrict()
    selectedBlockSession.value = settingsManager.getSetting("BlockSession", "false").toBooleanStrict()

    val checkedTheme by rememberUpdatedState(selectedTheme.value)
    val checkedLanguage by rememberUpdatedState(selectedLanguage.value)
    val checkedAutoLogin = rememberUpdatedState(selectedAutoLogin.value)
    val checkedBlockSession = rememberUpdatedState(selectedBlockSession.value)

    val context = LocalContext.current

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
                        if (checkedTheme == "Light") stringResource(R.string.themeL) else stringResource(
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
                        if (checkedLanguage == "pt") stringResource(R.string.langPT) else stringResource(
                            R.string.langEN
                        ), color = MaterialTheme.colorScheme.onSecondary
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
                    checked = checkedAutoLogin.value,
                    onCheckedChange = {
                        selectedAutoLogin.value = !checkedAutoLogin.value
                        settingsManager.saveSetting(
                            "AutoLogin",
                            (!checkedAutoLogin.value).toString()
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
                context
            )

            showWarn.value -> {
                Box(contentAlignment = Alignment.BottomCenter) {
                    Snackbar(text = stringResource(R.string.resetPassStext))
                    LaunchedEffect(Unit) {
                        delay(3000)
                        showWarn.value = false
                    }
                }
            }
        }
    }
}