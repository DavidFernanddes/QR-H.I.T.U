package com.example.qr_hitu

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationManagerCompat
import com.example.qr_hitu.ui.theme.QRHITUTheme


class MainActivity : ComponentActivity() {

    val Channel_ID = "channelID"
    val Channel_NAME = "channelName"
    val NOTIFICATION_ID = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                val notification = notificationBuilder(this, Channel_ID)

                val notificationManager = NotificationManagerCompat.from(this)

                notificationManager.cancelAll()

                CreateQrCode(notificationManager, NOTIFICATION_ID, notification)
            }
        }
        createNotificationChannel(Channel_ID, Channel_NAME, this)
    }
}



@Composable
fun CreateQrCode(notificationManager : NotificationManagerCompat, NOTIFICATION_ID : Int, notification : Notification) {

    var content by remember { mutableStateOf("") }
    var qrName by remember { mutableStateOf("") }
    val context = LocalContext.current

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {  data ->
                PopUp(context, data.message, data.actionLabel.toString())
            }
        )},
        backgroundColor = Color.White
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CreateQR(content)

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
                    unfocusedBorderColor = Color(0xFFBB86FC)
                )

            )

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = qrName,
                onValueChange = {
                    qrName = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = "Escreva o nome do ficheiro", color = Color(0xFFBB86FC)) },
                placeholder = { Text(text = "Nome", color = Color.LightGray) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = Color(0xFFBB86FC)
                )

            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                DownloadQR(content, qrName, context)
                showPopUp(scope, snackbarHostState)
            }) {
                Text("Download QR Code")
            }

            Button(onClick = {
                notificationManager.notify(NOTIFICATION_ID, notification)
            }) {
                Text("Show notification")
            }
        }
    }
}
