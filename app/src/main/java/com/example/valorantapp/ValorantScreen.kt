package com.example.valorantapp

import androidx.activity.compose.BackHandler // Importante!
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
import coil.compose.AsyncImage
import com.example.valorantapp.ui.theme.ValorantDarkBg
import com.example.valorantapp.ui.theme.ValorantRed
import com.example.valorantapp.ui.theme.ValorantTextSecondary
import org.koin.androidx.compose.koinViewModel

@Composable
fun ValorantScreen(viewModel: ValorantViewModel = koinViewModel()) {
    val selectedAgent = viewModel.selectedAgent

    // INTERCEPTA O BOTÃO DE VOLTAR DO ANDROID
    // Se houver um agente selecionado, o botão de voltar limpa a seleção em vez de fechar o app
    BackHandler(enabled = selectedAgent != null) {
        viewModel.selectAgent(null)
    }

    if (selectedAgent != null) {
        AgentDetailScreen(
            agent = selectedAgent,
            onBackClick = { viewModel.selectAgent(null) }
        )
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ValorantDarkBg)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        // Cabeçalho
        Text(
            text = "AGENTES VALORANT",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = ValorantRed,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Barra de Pesquisa
        OutlinedTextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
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

        when (val state = viewModel.uiState) {
            is ValorantUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = ValorantRed)
                }
            }

            is ValorantUiState.Error -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = state.message, color = Color.White)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = { viewModel.loadAgents() },
                        colors = ButtonDefaults.buttonColors(containerColor = ValorantRed)
                    ) {
                        Text("Tentar Novamente", color = Color.White)
                    }
                }
            }

            is ValorantUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.agents,
                        key = { agent -> agent.uuid }
                    ) { agent: AgentModel ->
                        Card(
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { viewModel.selectAgent(agent) }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AsyncImage(
                                    model = agent.displayIcon,
                                    contentDescription = agent.displayName,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(85.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = agent.displayName.uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )

                                agent.role?.let { role ->
                                    Text(
                                        text = role.displayName,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = ValorantRed,
                                        textAlign = TextAlign.Center,
                                        git init
                                                git add .
                                                git commit -m "feat: Projeto Valorant Hub finalizado"
                                                git branch -M main
                                                git remote add origin https://github.com/matheuscoutosz/Valorant-Hub.git
                                        git push -u origin main                                    maxLines = 1
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