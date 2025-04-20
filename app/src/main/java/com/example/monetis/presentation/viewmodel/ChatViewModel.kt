package com.example.monetis.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monetis.data.repository.GigaChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: GigaChatRepository
) : ViewModel() {

    private val _reply = MutableLiveData<String>()
    val reply: LiveData<String> = _reply

    fun ask(message: String) {
        viewModelScope.launch {
            try {
                val response = repository.sendMessage(message)
                _reply.value = response
            } catch (e: Exception) {
                _reply.value = "Ошибка: ${e.message}"
            }
        }
    }
}

