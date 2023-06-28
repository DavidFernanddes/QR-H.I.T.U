package com.example.qr_hitu.functions

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

//  Este ficheiro contém a logística para a app conseguir enviar um email

@Composable
@SuppressLint("CoroutineCreationDuringComposition")
fun SendEmail(
    email: String,  //  Estas variáveis contêm toda a informação para construir o email
    block: String,
    room: String,
    machine: String,
    problem: String,
    title: String,
    desc: String,
    urgent: Boolean
) {
    //  Variável que irá guardar todos os utilizadores para que tem de enviar email
    val receivers = remember { mutableStateOf("") }

    //  Detalhes para o código iniciar sessão no email corretamente
    val props = Properties().apply {
        put("mail.smtp.host", "smtp-mail.outlook.com")
        put("mail.smtp.port", "587")
        put("mail.smtp.auth", "true")
        put("mail.smtp.starttls.enable", "true")
    }

    //  Cria a sessão utilizando as informações da variável props e com o email/password da conta
    val session = Session.getInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(
                "qr.hitu@outlook.pt",
                decryptAES("XtzwiUBAr88MMzwxZVk1jQ==", encryptionKey)
            )
        }
    })

    //  Construção do texto do email
    var text =
        "O utilizador $email alertou $problem.\n\nEste Problema foi identificado no $block na $room no $machine.\n\n"
    if (problem == "uma avaria") {
        text += "Descrição do problema identificado: $desc.\n\n"
    }
    if (urgent) {
        text += "Este problema foi identificado como urgente!\n"
    }
    text += "Por favor verifique o problema o quanto antes!\n\n\n"
    text += "Não responda a este email."


    //  Busca na firestore para saber para quais contas tem de enviar email
    Firebase.firestore.collection("Admin").get().addOnCompleteListener {
        //  Guarda todos os emails na variável receivers
        it.result.documents.forEach{ doc ->
            receivers.value = receivers.value+doc.id+"/"
        }

        //  Repete para todos os emails
        for (x in receivers.value.split("/").indices){
            //  Cria o corpo do email
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress("qr.hitu@outlook.pt"))
                setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(receivers.value.split("/")[x])
                )
                subject = "$title - QR H.I.T.U."
                setText(text)
            }

            //  Chama a função para enviar
            send(message)
        }
    }
}

//  Função que envia o email
@OptIn(DelicateCoroutinesApi::class)
fun send(message: MimeMessage) {
    //  Abre coroutine
    GlobalScope.launch(Dispatchers.IO) {
        try {
            //  Envia email
            Transport.send(message)
        } catch (e: MessagingException) {
            //  Log caso exista algum erro
            Log.d("email error", e.message.toString())
        }
    }
}