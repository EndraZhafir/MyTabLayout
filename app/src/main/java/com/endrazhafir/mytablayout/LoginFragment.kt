package com.endrazhafir.mytablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogin = view.findViewById<Button>(R.id.btn_login)
        val linkRegister = view.findViewById<TextView>(R.id.link_register)

        btnLogin.setOnClickListener {
            // Navigate to Homepage after login
            (activity as MainActivity).navigateToHomepage()
        }

        linkRegister.setOnClickListener {
            (activity as MainActivity).navigateToRegister()
        }
    }
}