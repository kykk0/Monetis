package com.example.monetis.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.monetis.R
import com.example.monetis.data.repository.AuthRepository
import com.example.monetis.presentation.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        emailEditText = view.findViewById(R.id.emailEditText)
        passwordEditText = view.findViewById(R.id.passwordEditText)
        loginButton = view.findViewById(R.id.loginButton)
        registerButton = view.findViewById(R.id.registerButton)

        val auth = FirebaseAuth.getInstance()
        val repo = AuthRepository(auth)
        viewModel = AuthViewModel(repo)

        loginButton.setOnClickListener {
            viewModel.login(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        registerButton.setOnClickListener {
            viewModel.register(emailEditText.text.toString(), passwordEditText.text.toString())
        }

        lifecycleScope.launchWhenStarted {
            viewModel.authState.collect { result ->
                result?.onSuccess {
                    Toast.makeText(context, "Успех!", Toast.LENGTH_SHORT).show()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, AIChatFragment())
                        .commit()
                }?.onFailure {
                    Toast.makeText(context, "Ошибка: ${it.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}
