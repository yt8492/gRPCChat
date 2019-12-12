package com.yt8492.grpcchat.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yt8492.grpcchat.databinding.FragmentChatBinding
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ChatFragment : Fragment() {

    @Inject
    lateinit var chatViewModelFactory: ChatViewModelFactory


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

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    companion object {
        fun newInstance(): ChatFragment = ChatFragment()
    }
}