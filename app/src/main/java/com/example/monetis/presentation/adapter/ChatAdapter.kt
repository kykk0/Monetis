package com.example.monetis.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.monetis.R

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    private val messages = mutableListOf<Pair<String, Boolean>>()

    fun addMessage(text: String, isUser: Boolean) {
        messages.add(text to isUser)
        notifyItemInserted(messages.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val (text, isUser) = messages[position]
        holder.bind(text, isUser)
    }

    override fun getItemCount() = messages.size

    class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.messageTextView)

        fun bind(text: String, isUser: Boolean) {
            textView.text = text
            textView.textAlignment = if (isUser) View.TEXT_ALIGNMENT_TEXT_END else View.TEXT_ALIGNMENT_TEXT_START
            textView.setBackgroundResource(
                if (isUser) R.drawable.message_user_background else R.drawable.message_ai_background
            )
        }
    }
}

