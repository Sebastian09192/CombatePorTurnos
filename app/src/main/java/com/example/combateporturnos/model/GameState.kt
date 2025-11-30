package com.example.combateporturnos.model

data class GameState(
    val jugador1: Player,
    val jugador2: Player,
    val turnoJugadorId: Int = 1,
    val enCurso: Boolean = true,
    val ganadorId: Int? = null
)
