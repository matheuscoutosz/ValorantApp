package com.example.valorantapp.viewmodel

import com.example.valorantapp.model.AgentModel

sealed interface ValorantUiState {
    object Loading : ValorantUiState
    data class Success(val agents: List<AgentModel>) : ValorantUiState
    data class Error(val message: String) : ValorantUiState
}