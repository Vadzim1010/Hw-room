package com.example.hw_room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Details(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "user_id")
    val userId: Long,
    @ColumnInfo(name = "address")
    val address: String?,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String?,
)
