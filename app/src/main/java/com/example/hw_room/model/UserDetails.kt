package com.example.hw_room.model

import androidx.room.Embedded
import androidx.room.Relation

// I wonted to add a fragment with user details but I didn't have enough time to finish
// so I deleted all not finished code except db
data class UserDetails(
    @Embedded
    val user: User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_id"
    )
    val details: Details?
)
