package com.example.valorantapp.di

import com.example.valorantapp.model.ValorantApiService
import com.example.valorantapp.model.ValorantRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.valorantapp.viewmodel.ValorantViewModel

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://valorant-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ValorantApiService::class.java)
    }

    single { ValorantRepository(get()) }

    viewModel { ValorantViewModel(get()) }
}