package com.example.smartfridge

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopdata")
class ShopData {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "Id")
    var id: Int = 0
    @ColumnInfo(name = "Text")
    var text: String = ""

    constructor() {}

    constructor(text: String) {
        this.text = text
    }
}