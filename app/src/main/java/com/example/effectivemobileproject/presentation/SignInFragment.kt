package com.example.effectivemobileproject.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.effectivemobileproject.R
import com.example.effectivemobileproject.databinding.FragmentSignInBinding

class SignInFragment: Fragment(R.layout.fragment_sign_in) {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PagerSnapHelper()

        _binding = FragmentSignInBinding.bind(view)
    }
}