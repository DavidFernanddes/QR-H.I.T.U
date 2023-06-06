package com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList

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
import androidx.compose.material.icons.filled.Error
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
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.components.MalfInfo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class MalfunctionDocs(
    val machine: String,
    val room: String,
    val block: String,
    val urgent: Boolean,
)

private fun fetchDataFromFirestore(setList: (List<MalfunctionDocs>) -> Unit) {
    val firestore = Firebase.firestore
    firestore.collection("Avarias")
        .get()
        .addOnSuccessListener { documents ->
            val itemList = documents.mapNotNull { document ->
                val machine = document.getString("Dispositivo")
                val room = document.getString("Sala")
                val block = document.getString("Bloco")
                val urgent = document.getBoolean("Urgente")

                if (machine != null && room != null && block != null && urgent != null) {
                    MalfunctionDocs(machine, room, block, urgent)
                } else {
                    null
                }
            }

            val sortedList = itemList.sortedByDescending { it.urgent }
            setList(sortedList)
        }
        .addOnFailureListener { exception ->
            // Handle the failure
            Log.e("Firestore", "Error getting documents: ", exception)
        }
}

@Composable
fun MalfList(navController: NavController, viewModel: MalfunctionViewModel) {

    val (list, setList) = remember { mutableStateOf<List<MalfunctionDocs>>(emptyList()) }

    LaunchedEffect(Unit) {
        fetchDataFromFirestore(setList)
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
                    .clickable { navController.navigate(MalfInfo.route); viewModel.setSelectedMal(item.machine, item.room, item.block, item.urgent) },
                shape = MaterialTheme.shapes.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
                colors = CardDefaults.cardColors(Color(0xFFd9d9d9))
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    ) {

                    //TODO Comparação
                    //if(item.machine == "Projetor"){
                        if (item.machine == "Projetor") {
                            Icon(Icons.Filled.VideocamOff, "Projector")
                        } else if (item.machine == "Impressora"){
                            Icon(Icons.Filled.Print, "Impressora")
                        } else {
                            Icon(Icons.Filled.Computer, "Computer")
                        }
                    //}


                    Row(
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .width(240.dp),
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
                    if (item.urgent) {
                        Icon(Icons.Filled.Error, "Urgent", tint = Color.Red)
                    }
                }
            }
        }
    }
}