package com.example.qr_hitu.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class Malf(
    val qrString: String,
    val missQR: Boolean
)

class ScannerViewModel : ViewModel() {
    private val _myData = MutableLiveData<Malf>()
    val myData: LiveData<Malf> = _myData

    fun setMyData(code: String, missQR: Boolean) {
        val data = Malf(code, missQR)
        _myData.value = data
    }
}