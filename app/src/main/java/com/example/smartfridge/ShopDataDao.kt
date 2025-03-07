package com.example.smartfridge

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ShopDataDao {
    @Query("SELECT * FROM shopdata")
    fun getShopData(): LiveData<List<ShopData>>

    @Insert
    fun addShopData(shopData: ShopData)

    @Query("DELETE FROM shopdata WHERE Id = :id")
    fun deleteShopData(id: Int)
}