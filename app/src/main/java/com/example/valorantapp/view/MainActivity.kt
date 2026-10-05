package com.example.valorantapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.valorantapp.ui.theme.ValorantAppTheme

// A MainActivity é a classe de entrada (porta de acesso) padrão de qualquer app Android
class MainActivity : ComponentActivity() {

    // O método onCreate é executado automaticamente assim que o app é aberto
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ativa o modo de tela inteira (Edge-to-Edge), permitindo que o app ocupe o ecrã todo
        // (por detrás da barra de notificação e da barra de navegação do telemóvel)
        enableEdgeToEdge()

        // Define o conteúdo visual da Activity usando Jetpack Compose (substitui o antigo setContentView com ficheiros XML)
        setContent {
            // Aplica o tema visual personalizado do Valorant (cores, tipografia, estilos)
            ValorantAppTheme {
                // Surface é um contentor base do Material Design que pinta o fundo com a cor correta
                Surface(
                    modifier = Modifier.fillMaxSize(), // Ocupa o ecrã inteiro
                    color = MaterialTheme.colorScheme.background // Define a cor de fundo padrão baseada no tema
                ) {
                    // CHAMA A TELA PRINCIPAL: É aqui que o app "entra" de facto na tua lista de agentes (`ValorantScreen`)
                    ValorantScreen()
                }
            }
        }
    }
}