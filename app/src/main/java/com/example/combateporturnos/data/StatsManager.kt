package com.example.combateporturnos.data

import android.content.Context

data class PlayerStats(
    val victorias: Int,
    val derrotas: Int,
    val empates: Int
)

class StatsManager(context: Context) {

    private val prefs = context.getSharedPreferences("stats", Context.MODE_PRIVATE)

    private val KEY_HISTORY = "history" // historial básico de partidas

    // -------- ESTADÍSTICAS POR JUGADOR --------

    fun getStats(nombre: String): PlayerStats {
        val wins = prefs.getInt("${nombre}_wins", 0)
        val losses = prefs.getInt("${nombre}_losses", 0)
        val draws = prefs.getInt("${nombre}_draws", 0)
        return PlayerStats(wins, losses, draws)
    }

    fun addWin(nombre: String) {
        val key = "${nombre}_wins"
        val current = prefs.getInt(key, 0)
        prefs.edit().putInt(key, current + 1).apply()
    }

    fun addLoss(nombre: String) {
        val key = "${nombre}_losses"
        val current = prefs.getInt(key, 0)
        prefs.edit().putInt(key, current + 1).apply()
    }

    fun addDraw(nombre: String) {
        val key = "${nombre}_draws"
        val current = prefs.getInt(key, 0)
        prefs.edit().putInt(key, current + 1).apply()
    }

    // -------- HISTORIAL BÁSICO DE PARTIDAS --------
    // Guardamos las últimas 20 partidas como líneas de texto.

    fun addHistoryEntry(entry: String) {
        val raw = prefs.getString(KEY_HISTORY, "") ?: ""
        val list = if (raw.isBlank()) {
            mutableListOf<String>()
        } else {
            raw.split("||").toMutableList()
        }

        list.add(entry)
        // dejamos solo las últimas 20
        while (list.size > 20) {
            list.removeAt(0)
        }

        val newRaw = list.joinToString("||")
        prefs.edit().putString(KEY_HISTORY, newRaw).apply()
    }

    fun getHistory(): List<String> {
        val raw = prefs.getString(KEY_HISTORY, "") ?: ""
        if (raw.isBlank()) return emptyList()
        return raw.split("||")
    }
}
