package com.example.qr_hitu.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


//  Ficheiro com o viewModel para as avarias

//  Class de dados para mais facilmente aceder á informação
data class SelectedMalf(
    val name : String,
    val room : String,
    val block: String,
    val urgent : Boolean
)

//  ViewModel
class MalfunctionViewModel: ViewModel() {
    //  Variáveis que guardam a informação
    private val _myData = MutableLiveData<SelectedMalf>()
    val myData: LiveData<SelectedMalf> = _myData

    //  Procedimento que guarda a informação nas variáveis anteriores
    fun setSelectedMal(name: String, room: String, block: String, urgent: Boolean) {
        val data = SelectedMalf(name, room, block, urgent)
        _myData.value = data
    }
}