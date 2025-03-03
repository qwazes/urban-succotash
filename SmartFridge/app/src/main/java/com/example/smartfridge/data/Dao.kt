package com.example.smartfridge.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert
    suspend fun insertProduct(product: Product)

    @Query("SELECT * FROM products")
    fun getAllProducts() : Flow<List<Product>>

    @Query("SELECT * FROM products WHERE numberQR = :qr")
    fun getProductsByQr(qr: String) : Product?
}