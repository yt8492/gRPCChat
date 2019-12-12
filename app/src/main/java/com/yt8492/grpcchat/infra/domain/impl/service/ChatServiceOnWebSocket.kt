package com.yt8492.grpcchat.infra.domain.impl.service

import android.util.Log
import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.domain.service.ChatService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.Socket
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ChatServiceOnWebSocket @Inject constructor() : ChatService {

    override fun flowChatMessage(
        request: Flow<ChatMessage>
    ): Flow<ChatMessage> = channelFlow<ChatMessage> {
        withContext(Dispatchers.IO) {
            val clientSocket = Socket("10.0.2.2", 6789)
            val serverWriter = clientSocket.getOutputStream().bufferedWriter()
            val socketReader = clientSocket.getInputStream().bufferedReader()
            launch {
                socketReader.lineSequence()
                    .forEach {
                        channel.send(ChatMessage(it))
                    }
            }
            request.collect {
                withContext(Dispatchers.IO) {
                    serverWriter.write("${it.value}\n")
                    serverWriter.flush()
                }
            }
            awaitClose()
        }
    }
        .flowOn(Dispatchers.IO)
        .buffer()
}