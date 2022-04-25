package com.example.hw_room

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.hw_room.room.AppDatabase


class UserApplication : Application() {

    private var _appDatabase: AppDatabase? = null
    val appDatabase get() = requireNotNull(_appDatabase)

    override fun onCreate() {
        super.onCreate()
        _appDatabase = Room
            .databaseBuilder(
                this,
                AppDatabase::class.java,
                "room_database"
            )
            .allowMainThreadQueries()
            .build()
    }
}

val Context.appDatabase: AppDatabase
    get() = when (this) {
        is UserApplication -> appDatabase
        else -> applicationContext.appDatabase
    }



