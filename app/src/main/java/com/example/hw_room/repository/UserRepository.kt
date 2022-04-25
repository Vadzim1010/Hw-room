package com.example.hw_room.repository

import com.example.hw_room.model.User
import com.example.hw_room.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class UserRepository(private val appDatabase: AppDatabase) {

    private val userDao by lazy {
        appDatabase.userDao()
    }

    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAll()
    }

    fun insertUser(user: User): Long {
        return userDao.insertUser(user)
    }
}