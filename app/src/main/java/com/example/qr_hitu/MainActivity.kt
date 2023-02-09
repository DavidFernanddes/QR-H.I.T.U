package com.example.qr_hitu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.qr_hitu.ui.theme.QRHITUTheme
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                CreateQrCode()
            }
        }
    }
}

@Composable
fun CreateQrCode(modifier: Modifier = Modifier) {
    var content by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        if (content.isNotEmpty()){
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.QR_CODE, 512, 512)
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QrCode"

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Escreva texto para conversão", color = Color(0xFFBB86FC)) },
            placeholder = { Text(text = "Texto a converter", color = Color.LightGray) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color(0xFFBB86FC))

            )
    }
}