package com.yt8492.grpcchat.infra.api

import com.yt8492.grpcchat.protobuf.ChatServiceGrpc
import com.yt8492.grpcchat.protobuf.MessageRequest
import com.yt8492.grpcchat.protobuf.MessageResponse
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver
import javax.inject.Inject

class ChatApi @Inject constructor() {

    private val channel = ManagedChannelBuilder.forAddress("10.0.2.2", 6565)
        .usePlaintext()
        .build()

    private val chatServiceStub = ChatServiceGrpc.newStub(channel)

    fun observeChatMessage(
        onNext: (String) -> Unit,
        onError: (Throwable) -> Unit,
        onCompleted: () -> Unit
    ): StreamObserver<MessageRequest> {
        return chatServiceStub.execStream(object : StreamObserver<MessageResponse> {
            override fun onNext(value: MessageResponse?) {
                value?.message?.let(onNext)
            }

            override fun onError(t: Throwable?) {
                t?.let(onError)
            }

            override fun onCompleted() {
                onCompleted.invoke()
            }
        })
    }
}