package com.example.hw_room.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.hw_room.databinding.ItemUserBinding
import com.example.hw_room.listener.ItemClickListener
import com.example.hw_room.model.User
import com.example.hw_room.utils.toFirstNameStringFormat
import com.example.hw_room.utils.toLastNameStringFormat

class UserAdapter(
    context: Context,
    private val listener: ItemClickListener,
) : ListAdapter<User, UserViewHolder>(DIF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false),
            listener = listener
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val DIF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.firstName == newItem.firstName &&
                        oldItem.lastName == newItem.lastName
            }
        }
    }
}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val listener: ItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        with(binding) {
            firstNameTextView.text = user.firstName.toFirstNameStringFormat()
            lastNameTextView.text = user.lastName.toLastNameStringFormat()
            initButtons(user)
        }
    }

    private fun initButtons(user: User) = with(binding) {
        popupMenuButton.setOnClickListener {
            listener.onPopupMenuItemClickListener(user, popupMenuButton)
        }
    }
}
