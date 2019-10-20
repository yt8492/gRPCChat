package com.yt8492.grpcchat.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yt8492.grpcchat.domain.model.ChatMessage

class ChatRecyclerViewAdapter : ListAdapter<ChatMessage, ChatViewHolder>(CALLBACK) {

    private val messages = mutableListOf<ChatMessage>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder = ChatViewHolder.create(
        LayoutInflater.from(parent.context),
        parent,
        false
    )

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    fun add(message: ChatMessage) {
        messages.add(message)
        submitList(messages)
    }

    companion object {
        private val CALLBACK = object : DiffUtil.ItemCallback<ChatMessage>() {
            override fun areItemsTheSame(
                oldItem: ChatMessage,
                newItem: ChatMessage
            ): Boolean = oldItem == newItem

            override fun areContentsTheSame(
                oldItem: ChatMessage,
                newItem: ChatMessage
            ): Boolean = oldItem == newItem
        }
    }
}