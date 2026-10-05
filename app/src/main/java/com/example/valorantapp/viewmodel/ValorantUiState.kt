package com.example.valorantapp.viewmodel

import com.example.valorantapp.model.AgentModel

// aqui ficam os estados que a tela pode ter
// sealed = só pode ser um desses três, não tem outro
sealed interface ValorantUiState {

    // carregando (a tela mostra o círculo girando)
    object Loading : ValorantUiState

    // deu certo, guarda a lista de agentes pra tela mostrar
    data class Success(val agents: List<AgentModel>) : ValorantUiState

    // deu erro, guarda a mensagem pra tela mostrar
    data class Error(val message: String) : ValorantUiState
}