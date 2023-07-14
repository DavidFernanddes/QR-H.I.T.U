package com.example.qr_hitu.screens.adminScreens.tabLists.recentScanList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.qr_hitu.R
import com.example.qr_hitu.ViewModels.ScannerViewModel
import com.example.qr_hitu.components.ScannerAdminInfo
import com.example.qr_hitu.functions.SettingsManager
import com.example.qr_hitu.functions.loadListFromSettings
import com.example.qr_hitu.screens.adminScreens.tabLists.EmptyListScreen


//  Tela com a lista dos 5 ultimos QRs lidos pelo utilizador
@Composable
fun RecentScanList(navController: NavController, settingsManager: SettingsManager, viewModel: ScannerViewModel) {

    //  Estado que guarda a lista
    val (list, setList) = remember { mutableStateOf<List<String>>(emptyList()) }

    //  Abre Coroutine
    LaunchedEffect(Unit) {
        setList(loadListFromSettings(settingsManager).reversed())   //  Vai buscar a lista ás settings e guarda no estado
    }

    //  Caso não exista itens a apresentar
    if(list.isEmpty()){
        EmptyListScreen(text = stringResource(R.string.recentListText))
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 2.dp),
            columns = GridCells.Fixed(1)
        ) {
            //  Para cada item na lista cria um Card com as informações necessárias
            items(list) { item ->
                //  Pega nos valores guardados numa string
                val itemElements = item.split(",")
                //  Verifica se está a retornar a quantidade certa de campos
                if (itemElements.size >= 4) {
                    //  Apenas é necessário os room, machine, date por isso o primeiro campo está marcado como _
                    val (_, room, machine, date) = itemElements.take(4)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable {
                                //  Envia para a tela que mostra as especificações
                                navController.navigate(ScannerAdminInfo.route)
                                viewModel.setMyData(item)
                            },
                        shape = MaterialTheme.shapes.medium,
                        elevation = CardDefaults.cardElevation(defaultElevation = 7.dp),
                        colors = CardDefaults.cardColors(Color(0xFFd9d9d9))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            //  Verifica qual icon deve ser usado
                            if (machine == "Projetor") {
                                Icon(Icons.Filled.VideocamOff, "Projector")
                            } else {
                                Icon(Icons.Filled.Computer, "Computer")
                            }

                            Column(
                                modifier = Modifier,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(start = 5.dp)
                                        .width(270.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text(
                                        text = machine,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    Text(
                                        text = room,
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                }
                            }
                            Text(
                                text = "Visto a: $date",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}