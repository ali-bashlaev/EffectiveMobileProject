package com.example.effectivemobileproject.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Course
import com.example.domain.usecases.GetCoursesUseCase
import com.example.effectivemobileproject.presentation.state.MainState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getCoursesUseCase: GetCoursesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainState>(MainState.Loading)
    val state: StateFlow<MainState> = _state.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            try {
                val courses = getCoursesUseCase()
                _state.value = MainState.Success(courses)
            } catch (e: Exception) {
                _state.value = MainState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }
}
