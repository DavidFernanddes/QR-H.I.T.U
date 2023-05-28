package com.example.qr_hitu.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CreateQr1(
    val block : String,
    val room : String,
    val machine : String
)

data class CreateQr2(
    val name : String,
    val processor : String,
    val ram : String,
    val powerSupply : String
)

class ViewModel1 : ViewModel() {
    private val _myData = MutableLiveData<CreateQr1>()
    val myData: LiveData<CreateQr1> = _myData

    fun setMyData1(block: String, room: String, machine: String) {
        val data = CreateQr1(block, room, machine)
        _myData.value = data
    }
}

class ViewModel2 : ViewModel() {
    private val _myData = MutableLiveData<CreateQr2>()
    val myData: LiveData<CreateQr2> = _myData

    fun setMyData2(name : String, processor : String, ram : String, powerSupply: String) {
        val data = CreateQr2(name, processor, ram, powerSupply)
        _myData.value = data
    }
}