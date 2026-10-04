package model

class ValorantRepository(private val apiService: ValorantApiService) {
    suspend fun getAgents(): List<AgentModel> {
        return apiService.getAgents().data
    }
}