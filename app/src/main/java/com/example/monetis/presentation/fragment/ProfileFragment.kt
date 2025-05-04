package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.monetis.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val logoutButton: Button = view.findViewById(R.id.logoutButton)
        val emailTextView: TextView = view.findViewById(R.id.emailTextView)

        val user = FirebaseAuth.getInstance().currentUser
        emailTextView.text = user?.email ?: "Неизвестный пользователь"

        logoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            requireActivity().recreate()
        }
    }
}
