package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepo: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<Result<Unit>?>(null)
    val authState: StateFlow<Result<Unit>?> = _authState

    fun register(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = authRepo.register(email, password)
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = authRepo.login(email, password)
        }
    }
}
