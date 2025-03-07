package com.example.smartfridge

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DataDao {
    @Query("SELECT * FROM data")
    fun getData(): LiveData<List<Data>>

    @Insert
    fun addData(data: Data)

    @Query("UPDATE data SET quantity = quantity + 1 WHERE name = :Old_Name AND expiration_date = :Expiration_Date")
    fun updateQuantityPlus(Old_Name: String, Expiration_Date: String)

    @Query("UPDATE data SET all_weight = all_weight + one_weight WHERE name = :Old_Name AND expiration_date = :Expiration_Date")
    fun updateWeightPlus(Old_Name: String, Expiration_Date: String)

    @Query("UPDATE data SET quantity = quantity - 1 WHERE name = :Old_Name")
    fun updateQuantityMinus(Old_Name: String)

    @Query("UPDATE data SET all_weight = all_weight - one_weight WHERE name = :Old_Name")
    fun updateWeightMinus(Old_Name: String)

    @Query("DELETE FROM data WHERE name = :name")
    fun deleteData(name: String)
}