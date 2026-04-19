package com.example.domain.usecases

class ValidatePasswordUseCase {

    operator fun invoke(password: String?): Boolean {
        if (password.isNullOrBlank()) return false

        val isLongEnough = password.length >= 8

        val hasDigit = password.any { it.isDigit() }

        val hasLetter = password.any { it.isLetter() }

        return isLongEnough && hasDigit && hasLetter
    }
}