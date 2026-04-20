package com.example.effectivemobileproject.di

import com.example.domain.usecases.ValidatePasswordUseCase
import com.example.domain.usecases.GetCoursesUseCase
import com.example.effectivemobileproject.presentation.viewmodels.LoginViewModel
import com.example.effectivemobileproject.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory { ValidatePasswordUseCase() }

    // Provide the UseCase (depends on CourseRepository interface from :domain)
    factory { GetCoursesUseCase(get()) }

    viewModel { LoginViewModel(get()) }

    // Provide MainViewModel
    viewModel { MainViewModel(get()) }
}