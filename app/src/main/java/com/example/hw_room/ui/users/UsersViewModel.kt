package com.example.hw_room.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.hw_room.model.User
import com.example.hw_room.repository.UserRepository

class UsersViewModel(private val repository: UserRepository) : ViewModel() {

    private val users = repository.getAllUsers().asLiveData()

    fun getUsers(): LiveData<List<User>> {
        return users
    }
}

class UsersViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UsersViewModel(repository) as T
    }
}