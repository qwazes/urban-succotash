package com.example.smartfridge

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class DataViewModel(application: Application) : ViewModel() {

    val dataList: LiveData<List<Data>>
    private val repository: DataRepository

    var name by mutableStateOf("")
    var type by mutableStateOf("")
    var date_of_manufacture by mutableStateOf("")
    var expiration_date by mutableStateOf("")
    var all_weight by mutableStateOf(0f)
    var one_weight by mutableStateOf(0f)
    var proteins by mutableStateOf(0f)
    var fats by mutableStateOf(0f)
    var carbohydrates by mutableStateOf(0f)
    var kcal by mutableStateOf(0)
    var type_of_measurement by mutableStateOf("")
    var quantity by mutableStateOf(0)

    init {
        val dataDb = DataRoomDatabase.getInstance(application)
        val dataDao = dataDb.dataDao()
        repository = DataRepository(dataDao)
        dataList = repository.dataList
    }
    fun changeName(value: String){
        name = value
    }
    fun changeType(value: String){
        type = value
    }
    fun changeDate_of_manufacture(value: String){
        date_of_manufacture = value
    }

    fun changeExpiration_date(value: String){
        expiration_date = value
    }

    fun changeAllWeight(value: String){
        all_weight = value.toFloat()
    }

    fun changeOneWeight(value: String){
        one_weight = value.toFloat()
    }

    fun changeProteins(value: String){
        proteins = value.toFloat()
    }

    fun changeFats(value: String){
        fats = value.toFloat()
    }

    fun changeCarbohydrates(value: String){
        carbohydrates = value.toFloat()
    }

    fun changeKcal(value: String){
        kcal = value.toInt()
    }

    fun changeType_of_measurement(value: String){
        type_of_measurement = value
    }

    fun changeQuantity(value: String){
        quantity = value.toInt()
    }

    fun getData()
    {
        repository.getData()
    }
    fun addData() {
        repository.addData(Data(name, type, date_of_manufacture, expiration_date, all_weight, one_weight, proteins, fats, carbohydrates, kcal, type_of_measurement, quantity))
    }
    fun deleteData(name: String) {
        repository.deleteData(name)
    }

    fun updateQuantityPlus(Old_Name: String, Expiration_Date: String)
    {
        repository.updateQuantityPlus(Old_Name, Expiration_Date)
    }

    fun updateWeightPlus(Old_Name: String, Expiration_Date: String)
    {
        repository.updateWeightPlus(Old_Name, Expiration_Date)
    }

    fun updateQuantityMinus(Old_Name: String)
    {
        repository.updateQuantityMinus(Old_Name)
    }

    fun updateWeightMinus(Old_Name: String)
    {
        repository.updateWeightMinus(Old_Name)
    }
}