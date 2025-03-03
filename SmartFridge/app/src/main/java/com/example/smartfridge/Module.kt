package com.example.smartfridge

import android.app.Application
import androidx.room.Room
import com.example.smartfridge.data.MainDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object Module {

    @Provides
    @Singleton
    fun provideMainDb(app: Application) : MainDb
    {
        return Room.databaseBuilder(
            app,
            MainDb::class.java,
            "products.db").build()
    }
}