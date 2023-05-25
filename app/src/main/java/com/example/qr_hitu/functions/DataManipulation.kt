package com.example.qr_hitu.functions

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

val db = Firebase.firestore //  Variável que chama o serviço da firestore

//Procedimento para adicionar novo dispositivo
fun addDispositivo(
    bloco: String, //  As variáveis bloco, sala e ident são o caminho para o documento na firestore
    sala: String,
    ident: String,
    mapa: HashMap<String, String>  //  A variável mapa passa as informações a inserir
) {

    //  .collection() identifica a coleção, .document() identifica o documento
    //  .set() define a informação do documento atual
    db.collection("Inventário")
        .document(bloco)
        .collection(sala)
        .document(ident)
        .set(mapa)

}

//Procedimento para apagar dispositivo
fun delDispositivo(
    bloco: String, //  As variáveis bloco, sala e ident são o caminho para o documento na firestore
    sala: String,
    ident: String
) {

    //  .collection() identifica a coleção, .document() identifica o documento
    //  .delete() apaga o documento atual
    db.collection("Inventário")
        .document(bloco)
        .collection(sala)
        .document(ident)
        .delete()

}

//Função para buscar informação na firestore e guardar num mapa
@Composable
fun seeDispositivo(
    bloco: String, //  As variáveis bloco, sala e ident são o caminho para o documento na firestore
    sala: String,
    ident: String
): Map<String, Any> {

    //  A variável collectionReference foi criada para não deixar o código muito denso
    //  .collection() identifica a coleção, .document() identifica o documento
    val collectionReference = db.collection("Inventário")
        .document(bloco)
        .collection(sala)
        .document(ident)

    //  Variáveis para guardar os valores que vêm da firestore
    val (data, setData) = remember { mutableStateOf(mapOf<String, Any>()) }

    //  Abre coroutine
    LaunchedEffect(collectionReference) {
        try {
            //  .get() consegue o documentSnapshot, .await() espera que se recupere o documentSnapshot
            val documentSnapshot = collectionReference.get().await()
            //  Define o valor de setData para os valores no documentSnapshot, se for nulo define para nulo
            setData(documentSnapshot.data ?: mapOf())
        } catch (e: Exception) {
            //  Log em caso de erro
            Log.e("Firestore", "Error retrieving data", e)
        }
    }

    //  Retorna a data
    return data

}


fun addMalfunction(
    block: String,
    room: String,
    machine: String,
    malfunction: String,
    urgent: Boolean,
    email: String,
) {
    val data = hashMapOf(
        "Bloco" to block,
        "Sala" to room,
        "Dispositivo" to machine,
        "Avaria" to malfunction,
        "Urgente" to urgent,
        "Email" to email
    )

    db.collection("Avarias")
        .document("$room $machine")
        .set(data)
}