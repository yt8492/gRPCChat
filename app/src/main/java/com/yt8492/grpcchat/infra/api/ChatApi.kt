package com.yt8492.grpcchat.infra.api

import android.util.Log
import com.yt8492.grpcchat.protobuf.ChatServiceGrpc
import com.yt8492.grpcchat.protobuf.Empty
import com.yt8492.grpcchat.protobuf.MessageRequest
import com.yt8492.grpcchat.protobuf.MessageResponse
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver

class ChatApi {

    private val channel = ManagedChannelBuilder.forAddress("10.0.2.2", 6565)
        .usePlaintext()
        .build()

    private val stub = ChatServiceGrpc.newStub(channel)

    init {
        stub.healthCheck(Empty.newBuilder().build(), object : StreamObserver<Empty> {
            override fun onNext(value: Empty?) {
                Log.d("hogehoge", "health check")
            }

            override fun onError(t: Throwable?) {
            }

            override fun onCompleted() {
            }
        })
    }

    fun observeChatMessage(
        onNext: (String) -> Unit,
        onError: (Throwable) -> Unit,
        onCompleted: () -> Unit
    ): StreamObserver<MessageRequest> {
        return stub.execStream(object : StreamObserver<MessageResponse> {
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