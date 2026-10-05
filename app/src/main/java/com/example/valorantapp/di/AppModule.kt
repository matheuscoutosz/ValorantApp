package com.example.valorantapp.di

import com.example.valorantapp.model.ValorantApiService
import com.example.valorantapp.model.ValorantRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.valorantapp.viewmodel.ValorantViewModel

// aqui o Koin aprende a criar as coisas do app
val appModule = module {

    // conexão com a API (Retrofit)
    // single = cria uma vez só
    single {
        Retrofit.Builder()
            .baseUrl("https://valorant-api.com/")                 // site da API
            .addConverterFactory(GsonConverterFactory.create())   // transforma o JSON em objeto
            .build()
            .create(ValorantApiService::class.java)
    }

    // repository, pega o service de cima com o get()
    single { ValorantRepository(get()) }

    // ViewModel, pega o repository com o get()
    viewModel { ValorantViewModel(get()) }
}