package com.example.combateporturnos

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.example.combateporturnos.data.StatsManager
import com.example.combateporturnos.model.*
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

class GameViewModel(application: Application) : AndroidViewModel(application) {

    var jugador1 by mutableStateOf(Player(id = 1))
        private set
    var jugador2 by mutableStateOf(Player(id = 2))
        private set

    var turnoJugadorId by mutableStateOf(1)
        private set

    var enCurso by mutableStateOf(false)
        private set

    var ganadorId by mutableStateOf<Int?>(null)
        private set

    var logAccion by mutableStateOf("")
        private set

    private val statsManager = StatsManager(application)

    // -------- CONFIGURACIÓN INICIAL --------

    fun setNombres(nombre1: String, nombre2: String) {
        jugador1 = jugador1.copy(nombre = nombre1)
        jugador2 = jugador2.copy(nombre = nombre2)
    }

    fun setRazaYConfigJugador(
        idJugador: Int,
        raza: Race,
        arma: WeaponType?,
        elemento: ElementoMagico?
    ) {
        val baseVida = when (raza) {
            Race.ELFO -> if (elemento == ElementoMagico.AGUA) 115 else 100
            else -> 100
        }

        // configurar probabilidad de evasión y precisión extra según elemento
        var probEvasion = 0.0
        var bonoPrecision = 0.0

        if (raza == Race.ELFO) {
            when (elemento) {
                ElementoMagico.AIRE -> probEvasion = 0.25   // 25% de evasión
                ElementoMagico.TIERRA -> bonoPrecision = 0.07 // reduce bastante la probabilidad de fallo
                else -> {}
            }
        }

        val jugador = if (idJugador == 1) jugador1 else jugador2

        val actualizado = jugador.copy(
            raza = raza,
            arma = arma,
            elemento = elemento,
            vidaMax = baseVida,
            vidaActual = baseVida,
            distancia = 2,
            sangradoTurnos = 0,
            sangradoPorTurno = 0,
            curacionExtraPendiente = 0,
            probEvasion = probEvasion,
            bonoPrecision = bonoPrecision
        )

        if (idJugador == 1) {
            jugador1 = actualizado
        } else {
            jugador2 = actualizado
        }
    }

    fun iniciarCombate() {
        turnoJugadorId = 1
        enCurso = true
        ganadorId = null
        logAccion = "¡Comienza el combate!"
        aplicarEfectosInicioTurno() // por si hubiera algo pendiente
    }

    // -------- UTILIDADES INTERNAS --------

    private fun getJugadorActivo() = if (turnoJugadorId == 1) jugador1 else jugador2
    private fun getJugadorPasivo() = if (turnoJugadorId == 1) jugador2 else jugador1

    private fun actualizarJugadores(activo: Player, pasivo: Player) {
        if (turnoJugadorId == 1) {
            jugador1 = activo
            jugador2 = pasivo
        } else {
            jugador2 = activo
            jugador1 = pasivo
        }
    }

    // -------- MOVIMIENTO --------

    fun avanzar() {
        if (!enCurso) return
        val activo = getJugadorActivo()
        val pasivo = getJugadorPasivo()

        val nuevaDistancia = max(0, activo.distancia - 1)
        val nuevoActivo = activo.copy(distancia = nuevaDistancia)
        logAccion = "${activo.nombre} avanza. Distancia ahora: $nuevaDistancia"

        actualizarJugadores(nuevoActivo, pasivo)
        cambiarTurno()
    }

    fun retroceder() {
        if (!enCurso) return
        val activo = getJugadorActivo()
        val pasivo = getJugadorPasivo()

        val nuevaDistancia = min(2, activo.distancia + 1)
        val nuevoActivo = activo.copy(distancia = nuevaDistancia)
        logAccion = "${activo.nombre} retrocede. Distancia ahora: $nuevaDistancia"

        actualizarJugadores(nuevoActivo, pasivo)
        cambiarTurno()
    }

    // -------- ATAQUE --------

    fun atacar() {
        if (!enCurso) return

        var atacante = getJugadorActivo()
        var defensor = getJugadorPasivo()

        // 1) EVASIÓN: Elfo de aire puede esquivar el ataque
        val evade = if (defensor.probEvasion > 0.0) {
            val rnd = Random.nextDouble()
            rnd < defensor.probEvasion
        } else false

        if (evade) {
            logAccion = "${defensor.nombre} evade el ataque gracias a la magia de aire."
            cambiarTurno()
            return
        }

        // 2) POSIBLE FALLO: todos pueden fallar, pero Tierra tiene bono de precisión
        var probFallo = 0.10 // 10% base
        probFallo = max(0.0, probFallo - atacante.bonoPrecision) // tierra reduce la probabilidad de fallo

        val falla = Random.nextDouble() < probFallo
        if (falla) {
            logAccion = "${atacante.nombre} falla su ataque."
            cambiarTurno()
            return
        }

        // 3) Cálculo de daño
        val daño = calcularDanio(atacante, defensor)
        val vidaRestante = max(0, defensor.vidaActual - daño)
        defensor = defensor.copy(vidaActual = vidaRestante)

        logAccion = "${atacante.nombre} ataca y causa $daño puntos. " +
                "Vida de ${defensor.nombre}: $vidaRestante"

        // 4) EFECTOS ESPECIALES: Bestia con puños se hiere a sí misma
        if (atacante.raza == Race.BESTIA && atacante.arma == WeaponType.PUNIOS) {
            val selfDamage = 10
            val vidaAtacanteRestante = max(0, atacante.vidaActual - selfDamage)
            atacante = atacante.copy(vidaActual = vidaAtacanteRestante)
            logAccion += " | La bestia se hiere a sí misma (-$selfDamage). Vida propia: $vidaAtacanteRestante"
        }

        // 5) EFECTO ESPECIAL: ORCO con HACHA aplica sangrado por 2 turnos
        if (atacante.raza == Race.ORCO && atacante.arma == WeaponType.HACHA && vidaRestante > 0) {
            defensor = defensor.copy(
                sangradoTurnos = 2,
                sangradoPorTurno = 3
            )
            logAccion += " | ${defensor.nombre} sufre sangrado (-3 de vida por turno durante 2 turnos)."
        }

        actualizarJugadores(atacante, defensor)
        verificarGanador()
        if (enCurso) cambiarTurno()
    }

    private fun calcularDanio(atacante: Player, defensor: Player): Int {
        val baseRange: IntRange = when (atacante.raza) {
            Race.HUMANO -> when (atacante.arma) {
                WeaponType.ESCOPETA, WeaponType.RIFLE -> 1..5
                else -> 1..3
            }

            Race.ELFO -> 3..6

            Race.ORCO -> when (atacante.arma) {
                WeaponType.HACHA -> 1..5
                WeaponType.MARTILLO -> 2..7
                else -> 2..4
            }

            Race.BESTIA -> when (atacante.arma) {
                WeaponType.PUNIOS -> 20..30
                WeaponType.ESPADA -> 1..10
                else -> 4..8
            }

            else -> 1..4
        }

        var danio = Random.nextInt(baseRange.first, baseRange.last + 1)

        // Distancia afecta rifle francotirador y magia de aire
        if (atacante.raza == Race.HUMANO &&
            atacante.arma == WeaponType.RIFLE &&
            atacante.distancia == 2
        ) {
            danio += 10 // rifle hace más daño desde lejos
        }

        if (atacante.raza == Race.ELFO &&
            atacante.elemento == ElementoMagico.AIRE &&
            atacante.distancia == 2
        ) {
            danio = (danio * 1.2).toInt()
        }

        // Elemento fuego da más daño porcentual
        if (atacante.raza == Race.ELFO &&
            atacante.elemento == ElementoMagico.FUEGO
        ) {
            danio = (danio * 1.3).toInt()
        }

        return danio
    }

    // -------- SANACIÓN --------

    fun sanar() {
        if (!enCurso) return

        var activo = getJugadorActivo()

        val porcentaje: Int
        var extra = 0

        when (activo.raza) {
            Race.HUMANO -> {
                // comer: 40% a 49% aprox
                porcentaje = Random.nextInt(40, 50)
            }

            Race.ELFO -> {
                porcentaje = if (activo.elemento == ElementoMagico.AGUA) {
                    Random.nextInt(75, 91) // 75–90
                } else {
                    65 // elfo común
                }
            }

            Race.ORCO -> {
                // poción: porcentaje inicial + extra en el siguiente turno
                porcentaje = Random.nextInt(25, 46) // 25–45
                extra = Random.nextInt(5, 26)       // 5–25 para siguiente turno
            }

            Race.BESTIA -> {
                porcentaje = 50 // dormir: 50% de la vida perdida
            }

            else -> {
                porcentaje = 30
            }
        }

        val vidaPerdida = activo.vidaMax - activo.vidaActual
        val curado = (vidaPerdida * porcentaje / 100.0).toInt()
        var nuevaVida = min(activo.vidaMax, activo.vidaActual + curado)

        // configurar curación extra para ORCO
        if (activo.raza == Race.ORCO && extra > 0) {
            val vidaPerdidaDespues = activo.vidaMax - nuevaVida
            val curacionExtra = (vidaPerdidaDespues * extra / 100.0).toInt()
            activo = activo.copy(
                vidaActual = nuevaVida,
                curacionExtraPendiente = curacionExtra
            )
            logAccion = "${activo.nombre} toma una poción. Se cura un $porcentaje% ahora y tiene " +
                    "una curación extra pendiente de $curacionExtra puntos para el siguiente turno."
        } else {
            activo = activo.copy(vidaActual = nuevaVida)
            logAccion = "${activo.nombre} se cura un $porcentaje%. Vida ahora: ${activo.vidaActual}"
        }

        if (turnoJugadorId == 1) {
            jugador1 = activo
        } else {
            jugador2 = activo
        }

        cambiarTurno()
    }

    // -------- EFECTOS AL INICIO DEL TURNO --------

    private fun aplicarEfectosInicioTurno() {
        if (!enCurso) return

        var activo = getJugadorActivo()
        var textoExtra = ""

        // SANGRADO
        if (activo.sangradoTurnos > 0 && activo.sangradoPorTurno > 0) {
            val damage = activo.sangradoPorTurno
            val nuevaVida = max(0, activo.vidaActual - damage)
            activo = activo.copy(
                vidaActual = nuevaVida,
                sangradoTurnos = activo.sangradoTurnos - 1
            )
            textoExtra += " Sangrado afecta a ${activo.nombre} (-$damage). Vida: $nuevaVida."
        }

        // CURACIÓN EXTRA ORCO
        if (activo.curacionExtraPendiente > 0) {
            val heal = activo.curacionExtraPendiente
            val nuevaVida = min(activo.vidaMax, activo.vidaActual + heal)
            activo = activo.copy(
                vidaActual = nuevaVida,
                curacionExtraPendiente = 0
            )
            textoExtra += " Curación extra se aplica a ${activo.nombre} (+$heal). Vida: $nuevaVida."
        }

        if (turnoJugadorId == 1) {
            jugador1 = activo
        } else {
            jugador2 = activo
        }

        if (textoExtra.isNotBlank()) {
            logAccion = "${activo.nombre} comienza su turno.$textoExtra"
        }
    }

    private fun cambiarTurno() {
        if (!enCurso) return
        turnoJugadorId = if (turnoJugadorId == 1) 2 else 1
        aplicarEfectosInicioTurno()
        verificarGanador()
    }

    // -------- GANADOR + REGISTRO DE STATS / HISTORIAL --------

    private fun verificarGanador() {
        if (jugador1.vidaActual <= 0 && jugador2.vidaActual <= 0) {
            enCurso = false
            ganadorId = null
            logAccion += " | ¡Empate!"

            statsManager.addDraw(jugador1.nombre)
            statsManager.addDraw(jugador2.nombre)

            registrarHistorial("Empate entre ${jugador1.nombre} (${jugador1.raza}) y ${jugador2.nombre} (${jugador2.raza}).")
        } else if (jugador1.vidaActual <= 0) {
            enCurso = false
            ganadorId = 2
            logAccion += " | ${jugador2.nombre} gana."

            statsManager.addWin(jugador2.nombre)
            statsManager.addLoss(jugador1.nombre)

            registrarHistorial("${jugador2.nombre} (${jugador2.raza}/${jugador2.arma}) " +
                    "derrota a ${jugador1.nombre} (${jugador1.raza}/${jugador1.arma}). Vida restante: ${jugador2.vidaActual}.")
        } else if (jugador2.vidaActual <= 0) {
            enCurso = false
            ganadorId = 1
            logAccion += " | ${jugador1.nombre} gana."

            statsManager.addWin(jugador1.nombre)
            statsManager.addLoss(jugador2.nombre)

            registrarHistorial("${jugador1.nombre} (${jugador1.raza}/${jugador1.arma}) " +
                    "derrota a ${jugador2.nombre} (${jugador2.raza}/${jugador2.arma}). Vida restante: ${jugador1.vidaActual}.")
        }
    }

    private fun registrarHistorial(texto: String) {
        statsManager.addHistoryEntry(texto)
    }

    fun reiniciarPartida() {
        jugador1 = jugador1.copy(
            vidaActual = jugador1.vidaMax,
            distancia = 2,
            sangradoTurnos = 0,
            sangradoPorTurno = 0,
            curacionExtraPendiente = 0
        )
        jugador2 = jugador2.copy(
            vidaActual = jugador2.vidaMax,
            distancia = 2,
            sangradoTurnos = 0,
            sangradoPorTurno = 0,
            curacionExtraPendiente = 0
        )
        iniciarCombate()
    }

    // -------- API PARA LA PANTALLA DE ESTADÍSTICAS --------

    fun getHistorial(): List<String> = statsManager.getHistory()

    fun getStatsJugador(nombre: String) = statsManager.getStats(nombre)
}
