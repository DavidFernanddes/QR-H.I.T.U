package com.example.qr_hitu.functions

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Properties
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.MessagingException
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
fun sendEmail(
    email: String,
    block: String,
    room: String,
    machine: String,
    problem: String,
    title: String,
    desc: String,
    urgent: Boolean
) {
    val props = Properties().apply {
        put("mail.smtp.host", "smtp-mail.outlook.com") // Set the SMTP server
        put("mail.smtp.port", "587") // Set the SMTP server port
        put("mail.smtp.auth", "true") // Enable SMTP authentication
        put("mail.smtp.starttls.enable", "true") // Enable TLS encryption
    }

    val session = Session.getInstance(props, object : Authenticator() {
        override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication(
                "qr.hitu@outlook.pt",
                decryptAES("XtzwiUBAr88MMzwxZVk1jQ==", encryptionKey)
            )
        }
    })

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


    Firebase.firestore.collection("Admin").get().addOnCompleteListener {
        it.result.documents.forEach{ doc ->
            val message = MimeMessage(session).apply {
                setFrom(InternetAddress("qr.hitu@outlook.pt"))
                setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse(doc.id)
                )
                subject = "$title - QR H.I.T.U."
                setText(text)
            }

            send(message)

        }
    }

}

fun send(message: MimeMessage) {
    GlobalScope.launch(Dispatchers.IO) {
        try {
            Transport.send(message)
            // Email sent successfully
        } catch (e: MessagingException) {
            Log.d("email error", e.message.toString())
            // Handle error sending email

        }
    }
}