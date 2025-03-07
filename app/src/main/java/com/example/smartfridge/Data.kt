package com.example.smartfridge

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data")
class Data {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id: Int = 0
    @ColumnInfo(name = "Name")
    var name: String = ""
    @ColumnInfo(name = "Type")
    var type: String = ""
    @ColumnInfo(name = "Date_Of_Manufacture")
    var date_of_manufacture: String = ""
    @ColumnInfo(name = "Expiration_Date")
    var expiration_date: String = ""
    @ColumnInfo(name = "All_Weight")
    var all_weight: Float = 0f
    @ColumnInfo(name = "One_Weight")
    var one_weight: Float = 0f
    @ColumnInfo(name = "Proteins")
    var proteins: Float = 0f
    @ColumnInfo(name = "Fats")
    var fats: Float = 0f
    @ColumnInfo(name = "Carbohydrates")
    var carbohydrates: Float = 0f
    @ColumnInfo(name = "Kcal")
    var kcal: Int = 0
    @ColumnInfo(name = "Type_Of_Measurement")
    var type_of_measurement: String = ""
    @ColumnInfo(name = "Quantity")
    var quantity: Int = 0



    constructor() {}

    constructor(name: String,
                type: String,
                date_of_manufacture: String,
                expiration_date: String,
                all_weight: Float,
                one_weight: Float,
                proteins: Float,
                fats: Float,
                carbohydrates: Float,
                kcal: Int,
                type_of_measurement: String,
                quantity: Int) {
        this.name = name
        this.type = type
        this.date_of_manufacture = date_of_manufacture
        this.expiration_date = expiration_date
        this.all_weight = all_weight
        this.one_weight = one_weight
        this.proteins = proteins
        this.fats = fats
        this.carbohydrates = carbohydrates
        this.kcal = kcal
        this.type_of_measurement = type_of_measurement
        this.quantity = quantity
    }
}