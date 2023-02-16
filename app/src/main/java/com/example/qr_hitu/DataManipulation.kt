package com.example.qr_hitu

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

val db = Firebase.firestore

fun AddDispositivo (
    bloco : String,
    sala : String,
    ident : String,
    mapa : HashMap<String, String>
) {
    db.collection("Inventário")
        .document("$bloco")
        .collection("$sala")
        .document("$ident")
        .set(mapa)
}


fun DelDispositivo (
    bloco : String,
    sala : String,
    ident : String
) {
    db.collection("Inventário")
        .document("$bloco")
        .collection("$sala")
        .document("$ident")
        .delete()
}


