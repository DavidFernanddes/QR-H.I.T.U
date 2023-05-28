package com.example.qr_hitu.ViewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

data class SelectedMalf(
    val name : String,
    val room : String,
    val block: String,
    val urgent : Boolean
)

class MalfunctionViewModel: ViewModel() {
    private val _myData = MutableLiveData<SelectedMalf>()
    val myData: LiveData<SelectedMalf> = _myData

    fun setSelectedMal(name: String, room: String, block: String, urgent: Boolean) {
        val data = SelectedMalf(name, room, block, urgent)
        _myData.value = data
    }
}