package com.example.smartfridge

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ChartDataViewModel(application: Application) : ViewModel() {

    val chartdataList: LiveData<List<ChartData>>
    private val repository: ChartDataRepository

    var kcal by mutableStateOf(0)

    var day by mutableStateOf(0)
    var month by mutableStateOf(0)
    var year by mutableStateOf(0)

    init {
        val chartdataDb = ChartDataRoomDatabase.getInstance(application)
        val chartdataDao = chartdataDb.chartdataDao()
        repository = ChartDataRepository(chartdataDao)
        chartdataList = repository.chartdataList
    }
    fun changeKcal(value: Int){
        kcal = value
    }
    fun changeDay(value: Int){
        day = value
    }
    fun changeMonth(value: Int){
        month = value
    }
    fun changeYear(value: Int){
        year = value
    }
    fun getChartData()
    {
        repository.getChartData()
    }
    fun addChartData() {
        repository.addChartData(ChartData(kcal, day, month, year))
    }

    fun updateKcal(Kcal: Int, Day: Int, Month: Int, Year: Int)
    {
        repository.updateKcal(Kcal, Day, Month, Year)
    }
}