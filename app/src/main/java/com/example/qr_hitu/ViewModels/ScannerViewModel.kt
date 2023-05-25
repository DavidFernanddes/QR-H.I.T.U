package com.example.qr_hitu.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ScannerViewModel : ViewModel() {
    private val _myData = MutableLiveData<String>()
    val myData: LiveData<String> = _myData

    fun setMyData(code: String) {
        _myData.value = code
    }
}