package com.example.valorantapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.valorantapp.model.AgentModel
import com.example.valorantapp.model.ValorantRepository

class ValorantViewModel(
    private val repository: ValorantRepository
) : ViewModel() {

    var uiState: ValorantUiState by mutableStateOf(ValorantUiState.Loading)
        private set

    var searchQuery by mutableStateOf("")
        private set

    var selectedAgent by mutableStateOf<AgentModel?>(null)
        private set

    private var allAgents: List<AgentModel> = emptyList()

    init {
        loadAgents()
    }

    fun loadAgents() {
        uiState = ValorantUiState.Loading
        viewModelScope.launch {
            try {
                allAgents = repository.getAgents()
                applyFilter()
            } catch (e: Exception) {
                uiState = ValorantUiState.Error("Erro ao carregar agentes: ${e.localizedMessage}")
            }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
        applyFilter()
    }

    fun selectAgent(agent: AgentModel?) {
        selectedAgent = agent
    }

    private fun applyFilter() {
        val filtered = if (searchQuery.isBlank()) {
            allAgents
        } else {
            allAgents.filter { agent ->
                agent.displayName.contains(searchQuery, ignoreCase = true) ||
                        (agent.role?.displayName?.contains(searchQuery, ignoreCase = true) == true)
            }
        }
        uiState = ValorantUiState.Success(filtered)
    }
}