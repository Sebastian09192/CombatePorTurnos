package com.example.combateporturnos.model

data class Player(
    val id: Int,
    var nombre: String = "",
    var raza: Race? = null,
    var arma: WeaponType? = null,
    var elemento: ElementoMagico? = null, // solo elfo

    // Vida
    var vidaMax: Int = 100,
    var vidaActual: Int = 100,

    // Distancia (0 = cerca, 1 = media, 2 = lejos)
    var distancia: Int = 2,

    // Estadísticas persistentes
    var partidasGanadas: Int = 0,
    var partidasPerdidas: Int = 0,
    var partidasEmpatadas: Int = 0,

    // --------------------------
    // NUEVAS VARIABLES OBLIGATORIAS DEL PROYECTO
    // --------------------------

    // 🩸 SANGRADO (orco con hacha)
    var sangradoTurnos: Int = 0,        // cantidad de turnos con sangrado activo
    var sangradoPorTurno: Int = 0,      // daño fijo por turno durante sangrado

    // 💊 CURACIÓN EXTRA (orco)
    var curacionExtraPendiente: Int = 0, // curación que se aplicará en el siguiente turno

    // 🌪 EVASIÓN (elfo aire)
    var probEvasion: Double = 0.0,      // probabilidad de evadir un ataque (0.0 a 1.0)

    // 🪨 ACIERTO EXTRA (elfo tierra)
    var bonoPrecision: Double = 0.0     // probabilidad extra de que su ataque NO falle
)
