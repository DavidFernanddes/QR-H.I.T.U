package com.example.qr_hitu

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.example.qr_hitu.ui.theme.QRHITUTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QRHITUTheme {
                DB()
            }
        }
    }
}


@Composable
fun DB () {
    val db = Firebase.firestore

    var pc = hashMapOf(
        "Nome" to "Teste",
        "Processador" to "Intel",
        "Fonte" to "Cabo"
    )

    val collectionReference = db.collection("Inventário")
        .document("Bloco E")
        .collection("Sala E0.05")

    val (data, setData) = remember { mutableStateOf(mapOf<String, Any>()) }

    LaunchedEffect(collectionReference) {
        try {
            val documentSnapshot = collectionReference.document("Computador").get().await()
            setData(documentSnapshot.data ?: mapOf())
        } catch (e: Exception) {
            Log.e("Firestore", "Error retrieving data", e)
        }
    }

    var mostra = false

    Column {
        Button(onClick = { mostra = true }) {
            Text("Read")
        }
        if (mostra) {
            data.forEach { (key, value) ->
                Text("$key: $value")
            }
        }
        Add(pc)
        Del(db)
    }

}

@Composable
fun Add (pc : HashMap<String, String>) {
    Button(onClick = {
        AddDispositivo(bloco = "Bloco", sala = "Sala E0.05", ident = "ads", pc)
    }) {
        Text(text = "Add")
    }
}

@Composable
fun Del (db : FirebaseFirestore) {
    Button(onClick = {
        DelDispositivo(bloco = "Bloco", sala = "Sala E0.05", ident = "ads")
    }) {
        Text(text = "Delete")
    }
}


@Preview(showSystemUi = true)
@Composable
fun DefaultPreview() {
    QRHITUTheme {
        DB()
    }
}

