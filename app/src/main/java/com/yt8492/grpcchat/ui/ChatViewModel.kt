package com.yt8492.grpcchat.ui

import androidx.lifecycle.*
import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.domain.service.ChatService
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatViewModel(
    chatService: ChatService
) : ViewModel() {

    private val myMassage = MutableLiveData<ChatMessage>()

    val receiveMessage = chatService.flowChatMessage(myMassage.asFlow())
        .asLiveData()

    fun sendMessage(message: String): Job = viewModelScope.launch {
        myMassage.value = ChatMessage(message)
    }
}