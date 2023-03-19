package com.example.qr_hitu.screens.adminScreens.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QrCreate1ViewModel : ViewModel() {

    var selectedBlock by mutableStateOf("")
    var selectedRoom by mutableStateOf("")
    var selectedMachine by mutableStateOf("")

    fun onBlockSelected(block: String) {
            selectedBlock = block
    }

    fun onRoomSelected(room: String) {
            selectedRoom = room
    }

    fun onMachineSelected(machine: String) {
            selectedMachine = machine
    }
}

class QrCreate2ViewModel : ViewModel() {

    var name by mutableStateOf("")
    var processor by mutableStateOf("")
    var ram by mutableStateOf("")
    var powerSupply by mutableStateOf("")

    fun onNameWtritten(name: String) {
        this.name = name
    }

    fun onProcessorWtritten(processor: String) {
        this.processor = processor
    }

    fun onRamWtritten(ram: String) {
        this.ram = ram
    }
    fun onPowerSupplyWtritten(powerSupply: String) {
        this.powerSupply = powerSupply
    }
}