package com.example.combateporturnos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.combateporturnos.GameViewModel
import com.example.combateporturnos.R

@Composable
fun StatsScreen(
    gameViewModel: GameViewModel,
    onVolver: () -> Unit
) {
    val panel = Color(0xFFF6E7C1).copy(alpha = 0.92f)

    val j1 = gameViewModel.jugador1
    val j2 = gameViewModel.jugador2

    val stats1 = remember(j1.nombre) { gameViewModel.getStatsJugador(j1.nombre) }
    val stats2 = remember(j2.nombre) { gameViewModel.getStatsJugador(j2.nombre) }

    val historial = remember { gameViewModel.getHistorial().reversed() } // últimas partidas, de la más nueva a la más vieja

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_summary),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Estadísticas y Historial",
                modifier = Modifier
                    .background(panel, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color(0xFF2A1F0F)
            )

            // Tarjetas de estadísticas de los dos jugadores actuales (si ya jugaron)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Jugador: ${j1.nombre}")
                        Text("Victorias: ${stats1.victorias}")
                        Text("Derrotas: ${stats1.derrotas}")
                        Text("Empates: ${stats1.empates}")
                    }
                }

                Card(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier.padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Jugador: ${j2.nombre}")
                        Text("Victorias: ${stats2.victorias}")
                        Text("Derrotas: ${stats2.derrotas}")
                        Text("Empates: ${stats2.empates}")
                    }
                }
            }

            Text(
                "Historial de partidas",
                modifier = Modifier
                    .background(panel, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                color = Color(0xFF2A1F0F)
            )

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .background(panel.copy(alpha = 0.95f), RoundedCornerShape(12.dp))
                    .padding(8.dp)
            ) {
                if (historial.isEmpty()) {
                    Text("Aún no hay partidas registradas.")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(historial) { linea ->
                            Text("• $linea")
                        }
                    }
                }
            }

            Button(onClick = onVolver) {
                Text("Volver")
            }
        }
    }
}
