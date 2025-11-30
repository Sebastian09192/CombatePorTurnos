package com.example.combateporturnos.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.combateporturnos.GameViewModel
import com.example.combateporturnos.ui.screens.BattleScreen
import com.example.combateporturnos.ui.screens.RaceSelectionScreen
import com.example.combateporturnos.ui.screens.StartScreen
import com.example.combateporturnos.ui.screens.SummaryScreen
import com.example.combateporturnos.ui.screens.StatsScreen   // 👈 nueva pantalla

object Routes {
    const val START = "start"
    const val RACE_SELECTION = "race_selection"
    const val BATTLE = "battle"
    const val SUMMARY = "summary"
    const val STATS = "stats"   // 👈 NUEVA RUTA
}

@Composable
fun CombateApp(
    navController: NavHostController = rememberNavController(),
    gameViewModel: GameViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.START
    ) {
        // ------------------ PANTALLA DE INICIO ------------------
        composable(Routes.START) {
            StartScreen(
                onContinue = { nombre1, nombre2 ->
                    gameViewModel.setNombres(nombre1, nombre2)
                    navController.navigate(Routes.RACE_SELECTION)
                },
                onVerHistorial = {        // 👈 callback nuevo
                    navController.navigate(Routes.STATS)
                }
            )
        }

        // ------------------ SELECCIÓN DE RAZA ------------------
        composable(Routes.RACE_SELECTION) {
            RaceSelectionScreen(
                onJugadorConfigurado = { idJugador, raza, arma, elemento, ultimo ->
                    gameViewModel.setRazaYConfigJugador(idJugador, raza, arma, elemento)
                    if (ultimo) {
                        gameViewModel.iniciarCombate()
                        navController.navigate(Routes.BATTLE) {
                            popUpTo(Routes.START) { inclusive = false }
                        }
                    }
                }
            )
        }

        // ------------------ COMBATE ------------------
        composable(Routes.BATTLE) {
            BattleScreen(
                gameViewModel = gameViewModel,
                onFinPartida = {
                    navController.navigate(Routes.SUMMARY)
                }
            )
        }

        // ------------------ RESUMEN ------------------
        composable(Routes.SUMMARY) {
            SummaryScreen(
                gameViewModel = gameViewModel,
                onJugarDeNuevo = {
                    gameViewModel.reiniciarPartida()
                    navController.navigate(Routes.BATTLE) {
                        popUpTo(Routes.START) { inclusive = false }
                    }
                },
                onVolverInicio = {
                    navController.navigate(Routes.START) {
                        popUpTo(Routes.START) { inclusive = true }
                    }
                }
            )
        }

        // ------------------ ESTADÍSTICAS + HISTORIAL ------------------
        composable(Routes.STATS) {
            StatsScreen(
                gameViewModel = gameViewModel,
                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}
