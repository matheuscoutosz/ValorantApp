package com.example.valorantapp.model

import retrofit2.http.GET

interface ValorantApiService {
    // Adicionado &language=pt-BR para trazer os textos e funções em português
    @GET("v1/agents?isPlayableCharacter=true&language=pt-BR")
    suspend fun getAgents(): ValorantResponse
}