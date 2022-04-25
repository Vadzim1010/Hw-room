package com.example.hw_room.listener

import com.example.hw_room.model.User

interface ItemClickListener {

    fun onDeleteButtonItemClickListener(user: User)

    fun onUpdateItemClickListener(user: User)
}
