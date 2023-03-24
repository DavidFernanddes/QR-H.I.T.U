package com.example.qr_hitu.screens.adminScreens.scannerAdm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ScannerAdminViewModel : ViewModel() {
    private val _myData = MutableLiveData<String>()
    val myData: LiveData<String> = _myData

    fun setMyData(code: String) {
        _myData.value = code
    }
}