package com.example.effectivemobileproject.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.effectivemobileproject.R
import com.example.effectivemobileproject.databinding.FragmentLoginBinding

class LoginFragment: Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PagerSnapHelper()

        _binding = FragmentLoginBinding.bind(view)
    }
}