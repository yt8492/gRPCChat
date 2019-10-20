package com.yt8492.grpcchat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yt8492.grpcchat.databinding.ItemChatBinding
import com.yt8492.grpcchat.domain.model.ChatMessage

class ChatViewHolder(
    private val binding: ItemChatBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        chatMessage: ChatMessage
    ) {
        binding.chatMessage = chatMessage
        binding.executePendingBindings()
    }

    companion object {
        fun create(
            inflater: LayoutInflater,
            container: ViewGroup,
            attachToRoot: Boolean
        ): ChatViewHolder = ChatViewHolder(
            ItemChatBinding.inflate(
                inflater,
                container,
                attachToRoot
            )
        )
    }
}