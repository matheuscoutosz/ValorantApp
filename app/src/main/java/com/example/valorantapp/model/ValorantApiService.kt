package com.example.valorantapp.model

import retrofit2.http.GET

// O ValorantApiService é uma interface do Retrofit que define os "endpoints" (caminhos de rede) da API da Riot Games
interface ValorantApiService {

    // Anotação do Retrofit indicando que será feita uma requisição do tipo GET (busca de dados).
    // O URL inclui parâmetros úteis:
    // - isPlayableCharacter=true: traz apenas os agentes jogáveis (evita bots ou personagens de teste).
    // - language=pt-BR: garante que as biografias, nomes de habilidades e classes venham em português!
    @GET("v1/agents?isPlayableCharacter=true&language=pt-BR")

    // Função suspensa (suspend) que executa a requisição de forma assíncrona.
    // Ela devolve um objeto embrulhado chamado ValorantResponse (que contém a lista de agentes lá dentro).
    suspend fun getAgents(): ValorantResponse
}