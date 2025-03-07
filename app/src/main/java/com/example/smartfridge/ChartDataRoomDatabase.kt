package com.example.smartfridge

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ChartData::class)], version = 1)
abstract class ChartDataRoomDatabase: RoomDatabase() {

    abstract fun chartdataDao(): ChartDataDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: ChartDataRoomDatabase? = null
        fun getInstance(context: Context): ChartDataRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ChartDataRoomDatabase::class.java,
                        "chartdatadb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}