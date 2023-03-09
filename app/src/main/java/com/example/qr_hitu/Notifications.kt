package com.example.qr_hitu

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
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