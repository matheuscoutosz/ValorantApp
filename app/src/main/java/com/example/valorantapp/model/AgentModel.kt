package com.example.valorantapp.model

// a API devolve um JSON com uma lista de agentes dentro de "data"
// essa classe é o molde dessa resposta
data class ValorantResponse(
    val data: List<AgentModel>
)

// molde de um agente
// o ? depois do tipo quer dizer que pode vir nulo (vazio)
data class AgentModel(
    val uuid: String,                      // id único do agente
    val displayName: String,               // nome
    val description: String?,              // biografia
    val displayIcon: String?,              // link do ícone (usado na lista)
    val fullPortrait: String?,             // link do retrato grande (usado nos detalhes)
    val role: RoleModel?,                  // função (Duelista, Sentinela...)
    val abilities: List<AbilityModel>?     // lista de habilidades
)

// molde da função do agente
data class RoleModel(
    val displayName: String,   // nome da função
    val displayIcon: String?   // ícone da função
)

// molde de uma habilidade
data class AbilityModel(
    val slot: String,            // qual tecla/posição da habilidade
    val displayName: String,     // nome
    val description: String?,    // o que ela faz
    val displayIcon: String?     // ícone
)