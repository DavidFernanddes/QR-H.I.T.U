package com.example.qr_hitu.screens.adminScreens.tabLists.malfunctionsList

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.MalfunctionViewModel
import com.example.qr_hitu.components.MalfInfo
import com.example.qr_hitu.functions.MalfunctionDocs
import com.example.qr_hitu.functions.fetchMalfList
import com.example.qr_hitu.screens.adminScreens.tabLists.EmptyListScreen

//  Tela com a lista de todas os alertas de avarias guardados na base de dados
@Composable
fun MalfList(navController: NavController, viewModel: MalfunctionViewModel) {

    //  Variáveis para guardar a lista de avarias
    val (list, setList) = remember { mutableStateOf<List<MalfunctionDocs>>(emptyList()) }

    //  Abre Coroutine
    LaunchedEffect(Unit) {
        fetchMalfList(setList)  //  Chama a função fetchMalfList e guarda o resultado em setList
    }

    //  Caso não exista itens a apresentar
    if(list.isEmpty()){
        EmptyListScreen(text = stringResource(R.string.malfListText))
    } else {
        //  Cria uma lista vertical
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 2.dp),
            columns = GridCells.Fixed(1)
        ) {
            //  Para cada avaria na lista irá criar um Card com as informações pedidas
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

                        //  Condição para mudar o icon
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
                        //  Se for urgente mostra icon de urgent
                        if (item.urgent) {
                            Icon(Icons.Filled.Error, "Urgent", tint = Color.Red)
                        }
                    }
                }
            }
        }
    }


}