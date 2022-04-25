package com.example.hw_room

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.hw_room.repository.UserRepository
import com.example.hw_room.room.AppDatabase


class UserApplication : Application() {

    private var _appDatabase: AppDatabase? = null
    private val appDatabase get() = requireNotNull(_appDatabase)
    val repository by lazy {
        UserRepository(appDatabase)
    }

    override fun onCreate() {
        super.onCreate()
        _appDatabase = Room
            .databaseBuilder(
                this,
                AppDatabase::class.java,
                "room_database"
            )
            .build()
    }
}

val Context.repository: UserRepository
    get() = when (this) {
        is UserApplication -> repository
        else -> applicationContext.repository
    }



