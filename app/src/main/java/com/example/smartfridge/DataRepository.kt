package com.example.smartfridge

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DataRepository(private val dataDao: DataDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    val dataList: LiveData<List<Data>> = dataDao.getData()

    fun getData() {
        coroutineScope.launch(Dispatchers.IO) {
            dataDao.getData()
        }
    }

    fun addData(Data: Data) {
        coroutineScope.launch(Dispatchers.IO) {
            dataDao.addData(Data)
        }
    }

    fun deleteData(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            dataDao.deleteData(name)
        }
    }

    fun updateQuantityPlus(Old_Name: String, Expiration_Date: String)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dataDao.updateQuantityPlus(Old_Name, Expiration_Date)
        }
    }

    fun updateWeightPlus(Old_Name: String, Expiration_Date: String)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dataDao.updateWeightPlus(Old_Name, Expiration_Date)
        }
    }

    fun updateQuantityMinus(Old_Name: String)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dataDao.updateQuantityMinus(Old_Name)
        }
    }

    fun updateWeightMinus(Old_Name: String)
    {
        coroutineScope.launch(Dispatchers.IO)
        {
            dataDao.updateWeightMinus(Old_Name)
        }
    }
}