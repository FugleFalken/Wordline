package com.example.wordline

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.wordline.ui.screens.diagram.DiagramScreen
import com.example.wordline.ui.screens.settings.SettingsScreen

enum class Screens() {
    DIAGRAM,
    SETTINGS
}

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screens.DIAGRAM.name,
        modifier = modifier
    ) {
        composable(Screens.DIAGRAM.name) {
            DiagramScreen()
        }
        composable(Screens.SETTINGS.name) {
            SettingsScreen()
        }
    }
}