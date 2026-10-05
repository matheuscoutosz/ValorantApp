package com.example.valorantapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.valorantapp.model.AgentModel
import com.example.valorantapp.model.ValorantRepository

// aqui é o ViewModel, ele cuida dos dados da tela de agentes
// o repository é quem busca os agentes na API, o Koin entrega ele pra gente
class ValorantViewModel(
    private val repository: ValorantRepository
) : ViewModel() {

    // guarda como a tela tá agora: carregando, deu certo ou deu erro
    // com mutableStateOf a tela atualiza sozinha quando isso muda
    // private set = só o ViewModel mexe, a tela só lê
    var uiState: ValorantUiState by mutableStateOf(ValorantUiState.Loading)
        private set

    // o texto que a pessoa digitou na pesquisa
    var searchQuery by mutableStateOf("")
        private set

    // o agente que a pessoa clicou (se tá null é porque ninguém foi clicado)
    var selectedAgent by mutableStateOf<AgentModel?>(null)
        private set

    // lista com TODOS os agentes que veio da API
    // guardei ela pra filtrar sem ter que buscar na internet de novo toda hora
    private var allAgents: List<AgentModel> = emptyList()

    // roda quando o ViewModel nasce, já começa buscando os agentes
    init {
        loadAgents()
    }

    // busca os agentes na API (o botão "Tentar Novamente" também chama ela)
    fun loadAgents() {
        // primeiro põe a tela em carregando
        uiState = ValorantUiState.Loading

        // launch roda em segundo plano pra não travar a tela
        viewModelScope.launch {
            try {
                // pede os agentes pro repository
                allAgents = repository.getAgents()
                // deu certo, então filtra (se a busca tá vazia mostra todos)
                applyFilter()
            } catch (e: Exception) {
                // se deu ruim (tipo sem internet) mostra o erro
                uiState = ValorantUiState.Error("Erro ao carregar agentes: ${e.localizedMessage}")
            }
        }
    }

    // roda toda vez que a pessoa digita uma letra na pesquisa
    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery  // salva o texto
        applyFilter()           // filtra de novo
    }

    // quando clica num agente guarda ele aqui
    // quando volta passa null pra limpar
    fun selectAgent(agent: AgentModel?) {
        selectedAgent = agent
    }

    // aqui é o filtro da pesquisa
    private fun applyFilter() {
        val filtered = if (searchQuery.isBlank()) {
            // não digitou nada? mostra todo mundo
            allAgents
        } else {
            // digitou? deixa só quem tem o texto no nome ou na função (Duelista, Sentinela...)
            allAgents.filter { agent ->
                // ignoreCase = true pra não importar maiúscula ou minúscula
                agent.displayName.contains(searchQuery, ignoreCase = true) ||
                        // a função (role) pode ser nula, por isso o ?. e o == true
                        // pra não dar erro quando não tem função
                        (agent.role?.displayName?.contains(searchQuery, ignoreCase = true) == true)
            }
        }

        // manda a lista filtrada pra tela, e como o uiState mudou ela atualiza sozinha
        uiState = ValorantUiState.Success(filtered)
    }
}