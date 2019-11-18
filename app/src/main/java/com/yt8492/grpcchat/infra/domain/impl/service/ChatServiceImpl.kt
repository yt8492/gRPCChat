package com.yt8492.grpcchat.infra.domain.impl.service

import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.infra.api.ChatApi
import com.yt8492.grpcchat.protobuf.MessageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ChatServiceImpl(
    private val chatApi: ChatApi
) : com.yt8492.grpcchat.domain.service.ChatService {
    override fun flowChatMessage(
        send: Flow<ChatMessage>
    ): Flow<ChatMessage> = channelFlow<ChatMessage> {
        val observer = chatApi.observeChatMessage(
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
    }
        .flowOn(Dispatchers.IO)
        .conflate()
}