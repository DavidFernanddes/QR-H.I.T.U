package com.example.qr_hitu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.qr_hitu.screens.theme.QRHITUTheme
import com.example.qr_hitu.screens.userScreens.scannerInput


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                scannerInput()
            }
        }
    }
}
