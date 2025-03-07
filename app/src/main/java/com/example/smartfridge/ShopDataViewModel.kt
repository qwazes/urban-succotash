package com.example.smartfridge

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ShopDataViewModel(application: Application) : ViewModel() {

    val shopdataList: LiveData<List<ShopData>>
    private val repository: ShopDataRepository
    var Text by mutableStateOf("")

    init {
        val shopdataDb = ShopDataRoomDatabase.getInstance(application)
        val shopdataDao = shopdataDb.shopdataDao()
        repository = ShopDataRepository(shopdataDao)
        shopdataList = repository.shopdataList
    }
    fun changeText(value: String){
        Text = value
    }
    fun addShopData() {
        repository.addShopData(ShopData(Text))
    }
    fun deleteShopData(id: Int) {
        repository.deleteShopData(id)
    }
}