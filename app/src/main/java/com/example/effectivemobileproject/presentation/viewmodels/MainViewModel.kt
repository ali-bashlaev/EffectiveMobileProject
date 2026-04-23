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

    private var originalCourses: List<Course> = emptyList()
    private var isSorted: Boolean = false

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _state.value = MainState.Loading
            try {
                val courses = getCoursesUseCase()
                originalCourses = courses
                _state.value = MainState.Success(courses)
            } catch (e: Exception) {
                _state.value = MainState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    fun toggleFavorite(courseId: Int) {
        // Update both lists to maintain consistency when toggling sort
        originalCourses = originalCourses.map { course ->
            if (course.id == courseId) course.copy(hasLike = !course.hasLike) else course
        }

        val currentState = _state.value
        if (currentState is MainState.Success) {
            val updatedCourses = currentState.courses.map { course ->
                if (course.id == courseId) {
                    course.copy(hasLike = !course.hasLike)
                } else {
                    course
                }
            }
            _state.value = MainState.Success(updatedCourses)
        }
    }

    fun toggleSort() {
        val currentState = _state.value
        if (currentState is MainState.Success) {
            if (isSorted) {
                // Reset to original order
                _state.value = MainState.Success(originalCourses)
                isSorted = false
            } else {
                // Sort by publish date descending
                val sortedCourses = currentState.courses.sortedByDescending { it.publishDate }
                _state.value = MainState.Success(sortedCourses)
                isSorted = true
            }
        }
    }
}
