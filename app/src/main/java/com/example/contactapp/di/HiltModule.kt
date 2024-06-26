package com.example.contactapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.contacts.data.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    fun provideDatabase( application: Application) : ContactDatabase {

        return Room.databaseBuilder(
            application.applicationContext,
            ContactDatabase::class.java,
            "ContactApp.db"
        ).fallbackToDestructiveMigration().build()
    }

}