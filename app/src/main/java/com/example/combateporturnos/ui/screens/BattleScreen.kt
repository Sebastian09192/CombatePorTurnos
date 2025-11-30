package com.example.combateporturnos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.painterResource
import com.example.combateporturnos.GameViewModel
import com.example.combateporturnos.R
import com.example.combateporturnos.model.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

@Composable
fun BattleScreen(
    gameViewModel: GameViewModel,
    onFinPartida: () -> Unit
) {
    val j1 = gameViewModel.jugador1
    val j2 = gameViewModel.jugador2
    val turnoId = gameViewModel.turnoJugadorId
    val ganadorId = gameViewModel.ganadorId
    val log = gameViewModel.logAccion

    if (!gameViewModel.enCurso) {
        onFinPartida()
        return
    }

    val jugadorActivo = if (turnoId == 1) j1 else j2

    val imagenPersonaje = when (jugadorActivo.raza) {
        Race.HUMANO -> R.drawable.humano
        Race.ELFO -> R.drawable.elfo
        Race.ORCO -> R.drawable.orco
        Race.BESTIA -> R.drawable.bestia
        else -> R.drawable.ic_launcher_foreground
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // 🔥 Fondo RESPONSIVO
        Image(
            painter = painterResource(id = R.drawable.bg_battle),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            // 🔥 Panel blanco para TÍTULO
            Text(
                "⚔ Batalla ⚔",
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.8f))
                    .padding(6.dp)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White.copy(alpha = 0.85f)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(j1.nombre)
                        Text("Vida: ${j1.vidaActual}/${j1.vidaMax}")
                    }
                }
                Spacer(Modifier.width(8.dp))
                Card(modifier = Modifier.weight(1f)) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color.White.copy(alpha = 0.85f)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(j2.nombre)
                        Text("Vida: ${j2.vidaActual}/${j2.vidaMax}")
                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // 🔥 Personaje RESPONSIVO
                Image(
                    painter = painterResource(id = imagenPersonaje),
                    contentDescription = "Personaje activo",
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .aspectRatio(1f),
                    contentScale = ContentScale.Crop
                )

                Image(
                    painter = painterResource(id = R.drawable.vs),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                )
            }

            Spacer(Modifier.height(8.dp))

            // Botones
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { gameViewModel.atacar() }, modifier = Modifier.weight(1f)) {
                    Text("Atacar")
                }
                Button(onClick = { gameViewModel.sanar() }, modifier = Modifier.weight(1f)) {
                    Text("Sanar")
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = { gameViewModel.avanzar() }, modifier = Modifier.weight(1f)) {
                    Text("Avanzar")
                }
                Button(onClick = { gameViewModel.retroceder() }, modifier = Modifier.weight(1f)) {
                    Text("Retroceder")
                }
            }

            // Panel blanco para LOG
            Text(
                log,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.7f))
                    .padding(6.dp)
            )
        }
    }
}
