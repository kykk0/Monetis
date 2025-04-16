package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monetis.R
import com.example.monetis.presentation.adapter.ChatAdapter
import com.example.monetis.domain.entity.Message

class AIChatFragment : Fragment() {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var messageEditText: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflater.inflate(R.layout.fragment_ai_chat, container, false)

        val chatRecyclerView = binding.findViewById<RecyclerView>(R.id.chatRecyclerView)
        messageEditText = binding.findViewById(R.id.messageEditText)
        val sendButton = binding.findViewById<ImageButton>(R.id.sendButton)

        chatAdapter = ChatAdapter()
        chatRecyclerView.layoutManager = LinearLayoutManager(context)
        chatRecyclerView.adapter = chatAdapter

        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotEmpty()) {
                val message = Message(text = messageText)
                chatAdapter.addMessage(message)
                messageEditText.text.clear()
                chatRecyclerView.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }

        return binding
    }
}
