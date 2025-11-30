package com.example.combateporturnos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.combateporturnos.R

@Composable
fun StartScreen(
    onContinue: (String, String) -> Unit
) {
    val panel = Color(0xFFF6E7C1).copy(alpha = 0.92f) // pergamino claro

    var nombre1 by remember { mutableStateOf("") }
    var nombre2 by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_start),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {

            // TÍTULO
            Text(
                "⚔️ Videojuego de combate por turnos ⚔️",
                textAlign = TextAlign.Center,
                color = Color(0xFF2A1F0F),
                modifier = Modifier
                    .background(panel, shape = RoundedCornerShape(10.dp))
                    .padding(8.dp)
            )

            // ENTRADAS
            OutlinedTextField(
                value = nombre1,
                onValueChange = { nombre1 = it },
                label = { Text("Nombre Jugador 1") },
                modifier = Modifier
                    .background(panel, shape = RoundedCornerShape(10.dp))
                    .padding(6.dp)
            )

            OutlinedTextField(
                value = nombre2,
                onValueChange = { nombre2 = it },
                label = { Text("Nombre Jugador 2") },
                modifier = Modifier
                    .background(panel, shape = RoundedCornerShape(10.dp))
                    .padding(6.dp)
            )

            Button(
                onClick = { onContinue(nombre1.trim(), nombre2.trim()) },
                enabled = nombre1.isNotBlank() && nombre2.isNotBlank(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A5BA0))
            ) {
                Text("Continuar a selección de raza")
            }
        }
    }
}
