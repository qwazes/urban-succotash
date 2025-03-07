package com.example.smartfridge

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chartdata")
class ChartData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id: Int = 0
    @ColumnInfo(name = "Kcal")
    var kcal: Int = 0
    @ColumnInfo(name = "Day")
    var Day: Int = 0
    @ColumnInfo(name = "Month")
    var Month: Int = 0
    @ColumnInfo(name = "Year")
    var Year: Int = 0

    constructor() {}

    constructor(kcal: Int,
                Day: Int,
                Month: Int,
                Year: Int) {
        this.kcal = kcal
        this.Day = Day
        this.Month = Month
        this.Year = Year
    }
}