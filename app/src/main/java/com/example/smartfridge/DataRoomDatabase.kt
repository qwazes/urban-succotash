package com.example.smartfridge

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Data::class)], version = 1)
abstract class DataRoomDatabase: RoomDatabase() {

    abstract fun dataDao(): DataDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: DataRoomDatabase? = null
        fun getInstance(context: Context): DataRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataRoomDatabase::class.java,
                        "datadb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}