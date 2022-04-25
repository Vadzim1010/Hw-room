package com.example.hw_room.room

import androidx.room.Query
import com.example.hw_room.model.User

interface UserDao {

    @Query("SELECT * FROM User")
    fun getAll(): List<User>
}