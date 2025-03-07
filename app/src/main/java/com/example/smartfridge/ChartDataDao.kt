package com.example.smartfridge

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ChartDataDao {
    @Query("SELECT * FROM chartdata")
    fun getChartData(): LiveData<List<ChartData>>

    @Insert
    fun addChartData(chartdata: ChartData)

    @Query("UPDATE chartdata SET kcal = kcal + :Kcal WHERE Day = :Day AND Month = :Month AND Year = :Year")
    fun updateKcal(Kcal: Int, Day: Int, Month: Int, Year: Int)
}