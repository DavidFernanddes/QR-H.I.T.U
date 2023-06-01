package com.example.qr_hitu.functions


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.core.content.ContextCompat
/*
@SuppressLint("RememberReturnType")
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun FingerprintAuthentication(
    onAuthenticationSuccess: () -> Unit,
    onAuthenticationError: (String) -> Unit
) {
    val context = LocalContext.current
    val biometricManager = context.getSystemService(BiometricManager::class.java)

    when (biometricManager.canAuthenticate()) {
        BiometricManager.BIOMETRIC_SUCCESS -> {
            val promptInfo = remember {
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Fingerprint Authentication")
                    .setSubtitle("Please authenticate using your fingerprint")
                    .setNegativeButtonText("Cancel")
                    .build()
            }

            val biometricPrompt = remember {
                BiometricPrompt(
                    activity = context as FragmentActivity,
                    executor = ContextCompat.getMainExecutor(context),
                    callback = object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            onAuthenticationError(errString.toString())
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            onAuthenticationSuccess()
                        }
                    }
                )
            }

            LaunchedEffect(key1 = true) {
                biometricPrompt.authenticate(promptInfo)
            }
        }

        BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
            onAuthenticationError("No biometric hardware detected.")

        BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
            onAuthenticationError("Biometric hardware is currently unavailable.")

        BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
            onAuthenticationError("No fingerprints enrolled.")
    }
}
*/