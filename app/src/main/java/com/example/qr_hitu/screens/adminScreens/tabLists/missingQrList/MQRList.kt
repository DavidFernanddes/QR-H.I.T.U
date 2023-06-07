package com.example.qr_hitu.screens.adminScreens.tabLists.missingQrList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.functions.DelMissingDialog
import com.example.qr_hitu.functions.delMissing
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class MissingQrDocs(
    val machine: String,
    val room: String,
    val block: String,
)

private fun getMissingQR(setList: (List<MissingQrDocs>) -> Unit) {
    val db = Firebase.firestore.collection("Falta QR").get()
        .addOnSuccessListener {
            val itemList = it.mapNotNull { document ->
                val machine = document.getString("Dispositivo")
                val room = document.getString("Sala")
                val block = document.getString("Bloco")

                if (machine != null && room != null && block != null) {
                    MissingQrDocs(machine, room, block)
                } else {
                    null
                }
            }

            setList(itemList)
        }
        .addOnFailureListener { exception ->

            Log.e("Firestore", "Error getting documents: ", exception)
        }
}

@Composable
fun MissingQrList(navController: NavController) {

    val (list, setList) = remember { mutableStateOf<List<MissingQrDocs>>(emptyList()) }
    val show = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        getMissingQR(setList)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 2.dp),
        columns = GridCells.Fixed(1)
    ) {
        items(list) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .clickable { show.value = true },
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
                colors = CardDefaults.cardColors(Color(0xFFd9d9d9))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                ) {

                    when (item.machine) {
                        "Projetor" -> {
                            Icon(Icons.Filled.VideocamOff, "Projector")
                        }
                        "Impressora" -> {
                            Icon(Icons.Filled.Print, "Impressora")
                        }
                        else -> {
                            Icon(Icons.Filled.Computer, "Computer")
                        }
                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(280.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = item.machine,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = item.room,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
                if(show.value){
                    DelMissingDialog(onDialogDismissed = { show.value = false}, onDeleteClick = { delMissing(item.room, item.machine) })
                }
            }
        }
    }
}