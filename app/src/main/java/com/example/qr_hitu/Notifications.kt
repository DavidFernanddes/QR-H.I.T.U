package com.example.qr_hitu

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color.rgb
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun PopUp(context : Context, message : String, action : String) {
    //Criação da snackbar
    Snackbar(
        modifier = Modifier
            .padding(16.dp),
        backgroundColor = Color(0xFF64A9E2),
        elevation = 16.dp,
        contentColor = Color.White,
        action = {
            Text(
                modifier = Modifier.clickable {
                    val downloads = Environment.DIRECTORY_DOWNLOADS //Caminho para a pasta de downloads
                    val uri = Uri.parse(downloads)  //Cria referência Uri
                    var intent = Intent(Intent.ACTION_GET_CONTENT)  //Define a ação do botão
                    intent.setDataAndType(uri, "*/*")
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    context.startActivity(intent)   //Inicia ação
                },
                text = action
            )
        }
    ) {
        Text(message)
    }
}

//Mostrar PopUp
fun showPopUp(scope : CoroutineScope, snackbarHostState : SnackbarHostState) {
    //Inicia corotina
    scope.launch {
        //Torna snackbar visivel
        snackbarHostState.showSnackbar(
            //Definição dos parametros da snackbar
            message = "Imagem Transferida!",
            actionLabel = "Ir para Ficheiros",
            duration = SnackbarDuration.Short,
        )
    }

}

//Criação do canal para notificações
fun createNotificationChannel(Channel_ID: String, Channel_NAME: String, context: Context) {
    //Verifica-se a app
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        //Definição do canal
        val channel = NotificationChannel(Channel_ID, Channel_NAME, NotificationManager.IMPORTANCE_DEFAULT)
        //Definição do notification manager
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //Criar channel
        manager.createNotificationChannel(channel)
    }
}

//Criação do corpo da aplicação
fun notificationBuilder(context: Context, Channel_ID : String) : Notification {
    //Define a ação para ir para a aplicação
    val intent = Intent(context, MainActivity::class.java)

    //Define o pendingIntent para funcionar fora da aplicação
    val pendingIntent = PendingIntent.getActivity(
        context,
        0,
        intent,
        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
    )

    //retorna a notificação construida
    return NotificationCompat.Builder(context, Channel_ID)
        .setContentTitle("Avaria Nova")
        .setSmallIcon(androidx.core.R.drawable.notification_icon_background)
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .build()
}
