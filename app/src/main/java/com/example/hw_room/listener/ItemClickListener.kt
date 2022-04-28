package com.example.hw_room.listener

import android.view.View
import com.example.hw_room.model.User

interface ItemClickListener {

    fun onPopupMenuItemClickListener(user: User, view: View)
}
