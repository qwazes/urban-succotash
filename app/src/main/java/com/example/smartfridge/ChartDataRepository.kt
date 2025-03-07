package com.example.smartfridge

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChartDataRepository(private val chartdataDao: ChartDataDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val chartdataList: LiveData<List<ChartData>> = chartdataDao.getChartData()

    fun getChartData() {
        coroutineScope.launch(Dispatchers.IO) {
            chartdataDao.getChartData()
        }
    }

    fun addChartData(ChartData: ChartData) {
        coroutineScope.launch(Dispatchers.IO) {
            chartdataDao.addChartData(ChartData)
        }
    }
    fun updateKcal(Kcal: Int, Day: Int, Month: Int, Year: Int)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            chartdataDao.updateKcal(Kcal, Day, Month, Year)
        }
    }
}