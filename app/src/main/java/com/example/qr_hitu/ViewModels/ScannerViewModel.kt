package com.example.qr_hitu.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//  ViewModel do Scanner
class ScannerViewModel : ViewModel() {
    //  Variáveis que guardam a informação
    private val _myData = MutableLiveData<String>()
    val myData: LiveData<String> = _myData

    //  Procedimento que guarda a informação nas variáveis anteriores
    fun setMyData(code: String) {
        _myData.value = code
    }
}