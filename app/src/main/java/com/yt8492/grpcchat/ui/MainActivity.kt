package com.yt8492.grpcchat.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yt8492.grpcchat.R
import com.yt8492.grpcchat.domain.model.ChatMessage
import com.yt8492.grpcchat.infra.api.ChatService
import com.yt8492.grpcchat.infra.domain.impl.repository.ChatRepositoryImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val chatRepository = ChatRepositoryImpl(ChatService())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chatAdapter = ChatRecyclerViewAdapter()
        messageListView.apply {
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = chatAdapter
        }
        val sendMessageFlow = channelFlow<ChatMessage> {
            sendButton.setOnClickListener {
                val message = ChatMessage(messageEditText.text.toString())
                channel.offer(message)
            }
            awaitClose()
        }.conflate()
        lifecycleScope.launch {
            chatRepository.flowChatMessage(sendMessageFlow)
                .flowOn(Dispatchers.Main)
                .catch { e ->
                    e.printStackTrace()
                }
                .collect {
                    chatAdapter.add(it)
                }
        }
    }
}
