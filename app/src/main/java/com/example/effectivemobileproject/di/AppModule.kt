package com.example.effectivemobileproject.di

import com.example.domain.usecases.ValidatePasswordUseCase
import com.example.effectivemobileproject.presentation.viewmodels.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { ValidatePasswordUseCase() }

    viewModel { LoginViewModel(get()) }
}