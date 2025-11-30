package com.example.combateporturnos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.combateporturnos.GameViewModel
import com.example.combateporturnos.R

@Composable
fun SummaryScreen(
    gameViewModel: GameViewModel,
    onJugarDeNuevo: () -> Unit,
    onVolverInicio: () -> Unit
) {
    val panel = Color(0xFFF6E7C1).copy(alpha = 0.92f)

    val j1 = gameViewModel.jugador1
    val j2 = gameViewModel.jugador2
    val ganadorId = gameViewModel.ganadorId

    val mensajeGanador = when (ganadorId) {
        1 -> "🏆 Ganador: ${j1.nombre}"
        2 -> "🏆 Ganador: ${j2.nombre}"
        else -> "Empate 🤝"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_summary),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .background(panel, RoundedCornerShape(12.dp))
                .padding(20.dp)
        ) {
            Text(
                "Resumen Final",
                color = Color(0xFF2A1F0F)
            )

            Text(
                mensajeGanador,
                color = Color(0xFF2A1F0F)
            )

            Button(onClick = onJugarDeNuevo, colors = ButtonDefaults.buttonColors(Color(0xFF3A5BA0))) {
                Text("Revancha")
            }

            Button(onClick = onVolverInicio, colors = ButtonDefaults.buttonColors(Color(0xFF3A5BA0))) {
                Text("Volver al inicio")
            }
        }
    }
}
