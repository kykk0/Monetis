package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.monetis.R
import com.example.monetis.data.datasource.local.AppDatabase
import com.example.monetis.data.repository.ExpenseRepositoryImpl
import com.example.monetis.data.repository.GigaChatRepository
import com.example.monetis.presentation.adapter.ChatAdapter
import com.example.monetis.presentation.viewmodel.ChatViewModel

class AIChatFragment : Fragment() {

    private lateinit var viewModel: ChatViewModel
    private lateinit var adapter: ChatAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var input: EditText
    private lateinit var sendButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_ai_chat, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = ChatAdapter()

        recyclerView = view.findViewById(R.id.chatRecyclerView)
        input = view.findViewById(R.id.messageEditText)
        sendButton = view.findViewById(R.id.sendButton)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val db = AppDatabase.getDatabase(requireContext().applicationContext)
        val expenseRepository = ExpenseRepositoryImpl(db.expenseDao())

        val repository = GigaChatRepository(
            clientId = "e7d5bec7-b395-4389-ad85-469c801a47c5",
            clientSecret = "5c00d603-45c1-4516-b76c-1629e4e2bcca",
            expenseRepository = expenseRepository,
        )
// ZTdkNWJlYzctYjM5NS00Mzg5LWFkODUtNDY5YzgwMWE0N2M1OjVjMDBkNjAzLTQ1YzEtNDUxNi1iNzZjLTE2MjllNGUyYmNjYQ==

        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return ChatViewModel(repository) as T
            }
        }

        viewModel = ViewModelProvider(this, factory)[ChatViewModel::class.java]

        sendButton.setOnClickListener {
            val userMessage = input.text.toString().trim()
            if (userMessage.isNotEmpty()) {
                adapter.addMessage(userMessage, isUser = true)
                input.setText("")
                viewModel.ask(userMessage)
            }
        }

        viewModel.reply.observe(viewLifecycleOwner) { reply ->
            adapter.addMessage(reply, isUser = false)
            recyclerView.scrollToPosition(adapter.itemCount - 1)
        }
    }

}

