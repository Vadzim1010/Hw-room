package com.example.hw_room.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hw_room.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}
