package com.yt8492.grpcchat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yt8492.grpcchat.databinding.FragmentChatBinding
import com.yt8492.grpcchat.infra.api.ChatApi
import com.yt8492.grpcchat.infra.domain.impl.service.ChatServiceImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ChatFragment : Fragment() {

    private val chatService = ChatServiceImpl(ChatApi())
    private val chatViewModelFactory = ChatViewModelFactory(chatService)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentChatBinding.inflate(
            inflater,
            container,
            false
        )
        val chatAdapter = ChatRecyclerViewAdapter()
        binding.messageListView.apply {
            val layoutManager = LinearLayoutManager(context)
            val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
            addItemDecoration(dividerItemDecoration)
            adapter = chatAdapter
        }
        val chatViewModel = ViewModelProvider(
            this,
            chatViewModelFactory
        ).get(ChatViewModel::class.java)
        chatViewModel.receiveMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it.value, Toast.LENGTH_SHORT).show()
            chatAdapter.add(it)
        })
        binding.sendButton.setOnClickListener {
            val message = binding.messageEditText.text.toString()
            if (message.isNotBlank()) {
                chatViewModel.sendMessage(message)
                binding.messageEditText.setText("")
            }
        }
        return binding.root
    }

    companion object {
        fun newInstance(): ChatFragment = ChatFragment()
    }
}