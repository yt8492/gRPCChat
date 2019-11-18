package com.yt8492.grpcchat.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yt8492.grpcchat.domain.service.ChatService

class ChatViewModelFactory(
    private val chatService: ChatService
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        require(modelClass == ChatViewModel::class.java) {
            "unknown ViewModel class"
        }
        return ChatViewModel(chatService) as T
    }
}