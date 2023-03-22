package com.example.qr_hitu.screens.adminScreens.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CreateQr1(
    val block : String,
    val room : String,
    val machine : String
)

class ViewModel1 : ViewModel() {
    private val _myData = MutableLiveData<CreateQr1>()
    val myData: LiveData<CreateQr1> = _myData

    fun setMyData(block: String, room: String, machine: String) {
        val data = CreateQr1(block, room, machine)
        _myData.value = data
    }
}