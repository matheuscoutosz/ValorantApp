package com.example.valorantapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.valorantapp.ui.theme.ValorantDarkBg
import com.example.valorantapp.ui.theme.ValorantRed
import com.example.valorantapp.ui.theme.ValorantTextSecondary
import com.example.valorantapp.model.AgentModel

@Composable
fun AgentDetailScreen(
    agent: AgentModel,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ValorantDarkBg)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(14.dp)
    ) {
        // Botão de Voltar
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { onBackClick() }
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = "◄ VOLTAR",
                color = ValorantRed,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Imagem e Nome do Agente
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF16202A)),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = agent.fullPortrait ?: agent.displayIcon,
                            contentDescription = agent.displayName,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = agent.displayName.uppercase(),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White
                    )

                    agent.role?.let { role ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = role.displayName.uppercase(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = ValorantRed
                        )
                    }
                }
            }

            // Descrição/História Oficial
            item {
                agent.description?.let { desc ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF111923)),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "BIOGRAFIA",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = ValorantRed
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = desc,
                                fontSize = 13.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            // Título Habilidades
            item {
                Text(
                    text = "HABILIDADES",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White
                )
            }

            // Lista de Habilidades
            agent.abilities?.let { abilities ->
                items(abilities) { ability ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF16202A)),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            ability.displayIcon?.let { iconUrl ->
                                AsyncImage(
                                    model = iconUrl,
                                    contentDescription = ability.displayName,
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(end = 12.dp)
                                )
                            }
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = ability.displayName.uppercase(),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp,
                                    color = ValorantRed
                                )
                                ability.description?.let { abilityDesc ->
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = abilityDesc,
                                        fontSize = 11.sp,
                                        color = ValorantTextSecondary
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