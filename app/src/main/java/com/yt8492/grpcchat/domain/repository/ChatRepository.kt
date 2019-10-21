package com.yt8492.grpcchat.domain.repository

import com.yt8492.grpcchat.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun flowChatMessage(send: Flow<ChatMessage>): Flow<ChatMessage>
}