package com.yt8492.grpcchat.domain.service

import com.yt8492.grpcchat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatService {
    fun flowChatMessage(send: Flow<ChatMessage>): Flow<ChatMessage>
}