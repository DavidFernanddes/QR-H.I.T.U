package com.example.qr_hitu.screens.menu

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Snackbar
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.functions.EmailSnackbar
import com.example.qr_hitu.functions.InvalidSnackbar
import com.example.qr_hitu.functions.LanguageDialog
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.ThemeDialog
import com.example.qr_hitu.functions.recreateActivity
import com.example.qr_hitu.functions.setLocale
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.util.Locale

@Composable
fun SettingsOptions(navController: NavController, settingsManager: SettingsManager, switch: MutableState<String>) {

    val showWarn = remember { mutableStateOf(false) }

    val showThemeState = remember { mutableStateOf(false) }
    val showTheme by rememberUpdatedState(showThemeState.value)
    val showLanguageState = remember { mutableStateOf(false) }
    val showLanguage by rememberUpdatedState(showLanguageState.value)

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
    selectedLanguage.value = settingsManager.getSetting("Language", "")
    selectedAutoLogin.value = settingsManager.getSetting("AutoLogin", "false").toBooleanStrict()
    selectedBlockSession.value = settingsManager.getSetting("BlockSession", "false").toBooleanStrict()

    val checkedTheme by rememberUpdatedState(selectedTheme.value)
    val checkedLanguage by rememberUpdatedState(selectedLanguage.value)
    val checkedAutoLogin = rememberUpdatedState(selectedAutoLogin.value)
    val checkedBlockSession = rememberUpdatedState(selectedBlockSession.value)

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
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
                Text(stringResource(R.string.theme), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)

                Spacer(modifier = Modifier.padding(5.dp))

                Text(if (checkedTheme == "Light") stringResource(R.string.themeL) else stringResource(R.string.themeD), color = MaterialTheme.colorScheme.onSecondary)
            }
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.padding(5.dp))

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
                Text(stringResource(R.string.lang), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)

                Spacer(modifier = Modifier.padding(5.dp))

                Text( if (checkedLanguage == "pt") stringResource(R.string.langPT) else stringResource(R.string.langEN), color = MaterialTheme.colorScheme.onSecondary)
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
                    settingsManager.saveSetting("AutoLogin", (!checkedAutoLogin.value).toString())
                },
                enabled = true,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedThumbColor = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.primary)

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
                    settingsManager.saveSetting("BlockSession", (!checkedBlockSession.value).toString())
                },
                enabled = checkedAutoLogin.value,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    checkedTrackColor = MaterialTheme.colorScheme.primary,
                    checkedThumbColor = Color.White
                )
            )

        }

        Spacer(modifier = Modifier.padding(5.dp))

        Divider(thickness = DividerDefaults.Thickness, color = MaterialTheme.colorScheme.primary)

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
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(stringResource(R.string.changePass), style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSecondary)
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

            },
            shape = MaterialTheme.shapes.small
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ){
                Text(stringResource(R.string.aboutApp), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)
            }

        }
        when {
            showTheme -> ThemeDialog(
                showThemeState,
                selectedTheme,
                switch,
                settingsManager
            )
            showLanguage -> LanguageDialog(
                showLanguageState,
                selectedLanguage,
                settingsManager,
                context
            )
            showWarn.value -> {
                EmailSnackbar()
                LaunchedEffect(Unit) {
                    delay(3000)
                    showWarn.value = false
                }
            }
        }
    }
}