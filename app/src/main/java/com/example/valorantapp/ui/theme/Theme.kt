package com.example.valorantapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = ValorantRed,
    background = ValorantDarkBg,
    surface = ValorantCardBg,
    onPrimary = ValorantTextPrimary,
    onBackground = ValorantTextPrimary,
    onSurface = ValorantTextPrimary
)

@Composable
fun ValorantAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        content = content
    )
}