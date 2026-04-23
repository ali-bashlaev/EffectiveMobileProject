package com.example.effectivemobileproject.presentation.state

import com.example.domain.model.Course

sealed class MainState {
    object Loading : MainState()
    data class Success(val courses: List<Course>) : MainState()
    data class Error(val message: String) : MainState()
}