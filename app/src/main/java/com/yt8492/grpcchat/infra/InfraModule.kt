package com.yt8492.grpcchat.infra

import com.yt8492.grpcchat.domain.service.ChatService
import com.yt8492.grpcchat.infra.domain.impl.service.ChatServiceOnGrpc
import com.yt8492.grpcchat.infra.domain.impl.service.ChatServiceOnWebSocket
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@Module
class InfraModule {

    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideChatService(impl: ChatServiceOnGrpc): ChatService = impl

//    @ExperimentalCoroutinesApi
//    @Singleton
//    @Provides
//    fun provideChatService(impl: ChatServiceOnWebSocket): ChatService = impl
}