package com.example.valorantapp.view

import androidx.activity.compose.BackHandler // Importante para interceptar o botão físico de voltar do Android
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // Biblioteca Coil para carregar imagens da internet via URL
import com.example.valorantapp.viewmodel.ValorantUiState
import com.example.valorantapp.viewmodel.ValorantViewModel
import com.example.valorantapp.ui.theme.ValorantDarkBg
import com.example.valorantapp.ui.theme.ValorantRed
import com.example.valorantapp.ui.theme.ValorantTextSecondary
import com.example.valorantapp.model.AgentModel
import org.koin.androidx.compose.koinViewModel

// Função composable principal que desenha a tela de listagem e gere os estados do app
@Composable
fun ValorantScreen(viewModel: ValorantViewModel = koinViewModel()) { // Injeta o ViewModel automaticamente usando o Koin
    val selectedAgent = viewModel.selectedAgent // Observa se há algum agente selecionado para ver detalhes

    // INTERCEPTA O BOTÃO DE VOLTAR DO ANDROID
    // Se houver um agente selecionado, o botão de voltar limpa a seleção (volta pra lista) em vez de fechar o app
    BackHandler(enabled = selectedAgent != null) {
        viewModel.selectAgent(null)
    }

    // Se o usuário clicou em algum agente, exibe a tela de detalhes e interrompe a renderização desta tela
    if (selectedAgent != null) {
        AgentDetailScreen(
            agent = selectedAgent,
            onBackClick = { viewModel.selectAgent(null) }
        )
        return
    }

    // Estrutura principal da tela em Coluna (Layout vertical)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ValorantDarkBg) // Cor de fundo personalizada do tema
            .windowInsetsPadding(WindowInsets.safeDrawing) // Respeita as bordas/entalhes do telemóvel (status bar/notch)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        // Cabeçalho da Tela
        Text(
            text = "AGENTES VALORANT",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ValorantRed,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Barra de Pesquisa (Campo de texto reativo)
        OutlinedTextField(
            value = viewModel.searchQuery, // O texto exibido é o que está guardado no ViewModel
            onValueChange = { viewModel.onSearchQueryChange(it) }, // Quando o usuário digita, avisa o ViewModel para filtrar
            label = { Text("Pesquisar...", color = ValorantTextSecondary) },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ValorantRed,
                unfocusedBorderColor = ValorantTextSecondary,
                focusedLabelColor = ValorantRed,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ESTRUTURA REATIVA DE ESTADOS (Usa o padrão 'when' para olhar o uiState do ViewModel)
        when (val state = viewModel.uiState) {

            // ESTADO 1: Carregando (Mostra o indicador de progresso no centro)
            is ValorantUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ValorantRed)
                }
            }

            // ESTADO 2: Erro (Mostra a mensagem de falha e um botão para tentar de novo)
            is ValorantUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = state.message, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.loadAgents() }, // Tenta recarregar os dados da API
                        colors = ButtonDefaults.buttonColors(containerColor = ValorantRed)
                    ) {
                        Text("Tentar Novamente", color = Color.White)
                    }
                }
            }

            // ESTADO 3: Sucesso (Exibe a grelha com os agentes carregados ou filtrados)
            is ValorantUiState.Success -> {
                // Grelha vertical otimizada (Lazy Grid) com 3 colunas fixas
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.agents, // Lista de agentes que veio do estado Success
                        key = { agent -> agent.uuid } // Identificador único para otimizar a renderização
                    ) { agent: AgentModel ->
                        // Cartão individual de cada agente
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectAgent(agent) } // Ao clicar, seleciona o agente para ver detalhes
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // O COIL EM AÇÃO: Baixa e exibe a imagem do agente através da URL (`agent.displayIcon`)
                                AsyncImage(
                                    model = agent.displayIcon,
                                    contentDescription = agent.displayName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(8.dp)) // Arredonda os cantos da foto
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                // Nome do Agente em letras maiúsculas
                                Text(
                                    text = agent.displayName.uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )

                                // Função ou Classe do Agente (Ex: Duelista, Iniciador) se existir
                                agent.role?.let { role ->
                                    Text(
                                        text = role.displayName,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ValorantRed,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}