package com.example.effectivemobileproject.presentation

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.effectivemobileproject.R
import com.example.effectivemobileproject.databinding.FragmentLoginBinding
import com.example.effectivemobileproject.presentation.viewmodels.LoginViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)

        binding.loginEmailInput.doAfterTextChanged { text ->
            val email = text.toString()

            if (email.contains(" ")) {
                binding.loginEmailInput.setText(email.replace(" ", ""))
                binding.loginEmailInput.setSelection(binding.loginEmailInput.text.length)
            }
        }

        binding.loginProceedButton.setOnClickListener {
            val email = binding.loginEmailInput.text.toString()
            val password = binding.loginPasswordInput.text.toString()
            viewModel.onProceedClicked(email, password)
        }

        binding.loginVkButton.setOnClickListener {
            openBrowser("https://vk.com/")
        }


        binding.loginOkButton.setOnClickListener {
            openBrowser("https://ok.ru/")
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.emailError.collect { hasError ->
                binding.loginEmailInput.setBackgroundResource(
                    if (hasError) R.drawable.bg_input_error else R.drawable.bg_input_field
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.passwordError.collect { hasError ->
                binding.loginPasswordInput.setBackgroundResource(
                    if (hasError) R.drawable.bg_input_error else R.drawable.bg_input_field
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.navigateToMain.collect {
                findNavController().navigate(R.id.action_signIn_to_main)
            }
        }
    }

    private fun openBrowser(url: String) {
        val intent =
            android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}