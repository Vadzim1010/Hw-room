package com.example.hw_room.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDao::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}