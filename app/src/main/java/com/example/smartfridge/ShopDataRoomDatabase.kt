package com.example.smartfridge

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(ShopData::class)], version = 1)
abstract class ShopDataRoomDatabase: RoomDatabase() {

    abstract fun shopdataDao(): ShopDataDao

    // реализуем синглтон
    companion object {
        private var INSTANCE: ShopDataRoomDatabase? = null
        fun getInstance(context: Context): ShopDataRoomDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShopDataRoomDatabase::class.java,
                        "usersdb"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}