package com.example.data.repository

import android.util.Log
import com.example.data.network.ApiService
import com.example.domain.model.Course
import com.example.domain.repository.CourseRepository

class CourseRepositoryImpl(private val apiService: ApiService) : CourseRepository {
    override suspend fun getCourses(): List<Course> {
        return try {
            val response = apiService.getCourses()
            Log.d("CourseRepository", "Fetched ${response.courses.size} courses from API")
            response.courses.map { dto ->
                Course(
                    id = dto.id,
                    title = dto.title,
                    text = dto.text,
                    price = dto.price,
                    rate = dto.rate,
                    startDate = dto.startDate,
                    hasLike = dto.hasLike,
                    publishDate = dto.publishDate
                )
            }
        } catch (e: Exception) {
            Log.e("CourseRepository", "Error fetching courses: ${e.message}", e)
            throw e
        }
    }
}