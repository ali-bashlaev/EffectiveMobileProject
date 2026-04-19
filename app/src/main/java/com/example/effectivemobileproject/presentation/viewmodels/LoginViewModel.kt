package com.example.effectivemobileproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecases.ValidatePasswordUseCase
import com.example.domain.util.EmailValidator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _emailError = MutableStateFlow(false)
    val emailError: StateFlow<Boolean> = _emailError

    private val _passwordError = MutableStateFlow(false)
    val passwordError: StateFlow<Boolean> = _passwordError

    private val _navigateToMain = MutableSharedFlow<Unit>()
    val navigateToMain: SharedFlow<Unit> = _navigateToMain

    fun onProceedClicked(email: String, password: String) {
        val isEmailValid = EmailValidator.validate(email)
        val isPasswordValid = validatePasswordUseCase(password)

        // Update error states
        _emailError.value = !isEmailValid
        _passwordError.value = !isPasswordValid

        if (isEmailValid && isPasswordValid) {
            viewModelScope.launch {
                _navigateToMain.emit(Unit)
            }
        }
    }
}