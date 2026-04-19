package com.example.domain.usecases

import com.example.domain.util.EmailValidator

class LoginWithEmailUseCase {

    operator fun invoke(email: String): Boolean {
        return EmailValidator.validate(email)
    }
}