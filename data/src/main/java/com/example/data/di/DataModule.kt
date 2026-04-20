package com.example.data.di

import com.example.data.network.ApiService
import com.example.data.repository.CourseRepositoryImpl
import com.example.domain.repository.CourseRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://drive.usercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    single<CourseRepository> {
        CourseRepositoryImpl(get())
    }
}