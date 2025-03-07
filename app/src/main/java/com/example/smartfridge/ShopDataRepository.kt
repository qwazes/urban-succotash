package com.example.smartfridge

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopDataRepository(private val shopdataDao: ShopDataDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val shopdataList: LiveData<List<ShopData>> = shopdataDao.getShopData()

    fun addShopData(shopData: ShopData) {
        coroutineScope.launch(Dispatchers.IO) {
            shopdataDao.addShopData(shopData)
        }
    }

    fun deleteShopData(id: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            shopdataDao.deleteShopData(id)
        }
    }
}