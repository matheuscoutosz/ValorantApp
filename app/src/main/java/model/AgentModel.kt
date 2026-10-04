package model

data class ValorantResponse(
    val data: List<AgentModel>
)

data class AgentModel(
    val uuid: String,
    val displayName: String,
    val description: String?,
    val displayIcon: String?,
    val fullPortrait: String?,
    val role: RoleModel?,
    val abilities: List<AbilityModel>?
)

data class RoleModel(
    val displayName: String,
    val displayIcon: String?
)

data class AbilityModel(
    val slot: String,
    val displayName: String,
    val description: String?,
    val displayIcon: String?
)