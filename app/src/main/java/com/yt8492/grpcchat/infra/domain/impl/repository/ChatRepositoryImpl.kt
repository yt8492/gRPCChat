package com.yt8492.grpcchat.infra.domain.impl.repository

import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.domain.repository.ChatRepository
import com.yt8492.grpcchat.infra.api.ChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ChatRepositoryImpl @Inject constructor(
    val chatService: ChatService
) : ChatRepository {
    override suspend fun flowChatMessage(): Flow<ChatMessage> =
        withContext(Dispatchers.IO) {
        channelFlow<ChatMessage> {
            chatService.observeChatMessage(
                onNext = {
                    launch {
                        channel.send(ChatMessage(it))
                    }
                },
                onError = {
                    channel.close(it)
                },
                onCompleted = {
                    channel.close()
                }
            )
        }
    }
}