package com.example.hw_room.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.hw_room.model.UserDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailsDao {

    @Transaction
    @Query("SELECT * FROM User WHERE id LIKE (:userId) LIMIT 1")
    fun getUserDetails(userId: Long): Flow<UserDetails>
}
