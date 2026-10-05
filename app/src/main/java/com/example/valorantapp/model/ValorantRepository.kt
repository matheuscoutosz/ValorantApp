package com.example.valorantapp.model

// O Repositório é a classe responsável por mediar a comunicação entre a API e o ViewModel
class ValorantRepository(
    private val apiService: ValorantApiService // Recebe o serviço do Retrofit injetado (via Koin)
) {

    // Função suspensa (suspend fun) que busca os agentes de forma assíncrona,
    // permitindo ser executada em segundo plano sem travar a interface do usuário
    suspend fun getAgents(): List<AgentModel> {
        // Chama o Retrofit (`apiService`), que faz a requisição HTTP para a Riot Games,
        // e extrai apenas a lista de agentes de dentro do embrulho de resposta (`.data`)
        return apiService.getAgents().data
    }
}