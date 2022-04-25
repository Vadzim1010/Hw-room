package com.example.hw_room.ui.adduser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.hw_room.model.User
import com.example.hw_room.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDescViewModel(private val repository: UserRepository) : ViewModel() {

    fun insert(user: User): Long {
        var userId: Long = 0
        viewModelScope.launch(Dispatchers.IO) {
            userId = repository.insertUser(user)
        }
        return userId
    }
}

class UserDescViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDescViewModel(repository) as T
    }
}