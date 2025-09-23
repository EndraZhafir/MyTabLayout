package com.endrazhafir.mytablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnRegister = view.findViewById<Button>(R.id.btn_register)
        val linkLogin = view.findViewById<TextView>(R.id.link_login)

        btnRegister.setOnClickListener {
            // Navigate to Login after register
            (activity as MainActivity).navigateToLogin()
        }

        linkLogin.setOnClickListener {
            (activity as MainActivity).navigateToLogin()
        }
    }
}