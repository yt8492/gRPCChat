package com.yt8492.grpcchat.infra.domain.impl.repository

import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.domain.repository.ChatRepository
import com.yt8492.grpcchat.infra.api.ChatService
import com.yt8492.grpcchat.protobuf.MessageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ChatRepositoryImpl @Inject constructor(
    private val chatService: ChatService
) : ChatRepository {
    override suspend fun flowChatMessage(
        send: Flow<ChatMessage>
    ): Flow<ChatMessage> =
        withContext(Dispatchers.IO) {
            channelFlow<ChatMessage> {
                val observer = chatService.observeChatMessage(
                    onNext = {
                        channel.offer(ChatMessage(it))
                    },
                    onError = {
                        channel.close(it)
                    },
                    onCompleted = {
                    }
                )
                send.collect {
                    val req = MessageRequest.newBuilder()
                        .setMessage(it.value)
                        .build()
                    observer.onNext(req)
                }
                awaitClose()
            }.conflate()
        }
}