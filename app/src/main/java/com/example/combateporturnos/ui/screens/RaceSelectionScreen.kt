package com.example.combateporturnos.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.combateporturnos.R
import com.example.combateporturnos.model.*


// 🔥 FUNCIÓN GLOBAL PARA OBTENER LA IMAGEN DEL PERSONAJE
fun getCharacterImage(raza: Race?, arma: WeaponType?, elemento: ElementoMagico?): Int {
    return when (raza) {
        Race.HUMANO -> {
            when (arma) {
                WeaponType.RIFLE -> R.drawable.humano_rifle
                WeaponType.ESCOPETA -> R.drawable.humano
                else -> R.drawable.humano
            }
        }

        Race.ORCO -> {
            when (arma) {
                WeaponType.HACHA -> R.drawable.orco_hacha
                WeaponType.MARTILLO -> R.drawable.orco
                else -> R.drawable.orco
            }
        }

        Race.BESTIA -> {
            when (arma) {
                WeaponType.ESPADA -> R.drawable.bestia_espada
                WeaponType.PUNIOS -> R.drawable.bestia
                else -> R.drawable.bestia
            }
        }

        Race.ELFO -> {
            when (elemento) {
                ElementoMagico.FUEGO -> R.drawable.elfo_fuego
                ElementoMagico.AIRE -> R.drawable.elfo_aire
                ElementoMagico.TIERRA -> R.drawable.elfo_tierra
                ElementoMagico.AGUA -> R.drawable.elfo_agua
                else -> R.drawable.elfo_fuego
            }
        }

        else -> R.drawable.ic_launcher_foreground
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RaceSelectionScreen(
    onJugadorConfigurado: (
        idJugador: Int,
        Race,
        WeaponType?,
        ElementoMagico?,
        ultimo: Boolean
    ) -> Unit
) {
    val panel = Color(0xFFF6E7C1).copy(alpha = 0.92f)

    var jugadorActual by remember { mutableStateOf(1) }
    var razaSeleccionada by remember { mutableStateOf<Race?>(null) }
    var armaSeleccionada by remember { mutableStateOf<WeaponType?>(null) }
    var elementoSeleccionado by remember { mutableStateOf<ElementoMagico?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_race),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.align(Alignment.Center)
        ) {

            // 🔥 TÍTULO
            Text(
                "Configuración Jugador $jugadorActual ⚔",
                color = Color(0xFF2A1F0F),
                modifier = Modifier
                    .background(panel, RoundedCornerShape(10.dp))
                    .padding(8.dp)
            )


            // 🔥 PREVIEW DEL PERSONAJE (MUY PRO)
            if (razaSeleccionada != null) {
                val img = getCharacterImage(razaSeleccionada, armaSeleccionada, elementoSeleccionado)

                Image(
                    painter = painterResource(id = img),
                    contentDescription = "Preview",
                    modifier = Modifier
                        .size(150.dp)
                        .background(panel, RoundedCornerShape(12.dp))
                        .padding(6.dp),
                    contentScale = ContentScale.Fit
                )
            }



            // 🔥 SELECCIÓN DE RAZA
            Text(
                "Seleccione raza:",
                color = Color(0xFF2A1F0F),
                modifier = Modifier
                    .background(panel, RoundedCornerShape(10.dp))
                    .padding(6.dp)
            )


            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Race.values().forEach { race ->
                    FilterChip(
                        selected = razaSeleccionada == race,
                        onClick = {
                            razaSeleccionada = race
                            armaSeleccionada = null
                            elementoSeleccionado = null
                        },
                        label = {
                            Text(
                                race.name,
                                color = Color(0xFF2A1F0F),
                                modifier = Modifier
                                    .background(panel, RoundedCornerShape(8.dp))
                                    .padding(4.dp)
                            )
                        }
                    )
                }
            }


            // 🔥 OPCIONES DEPENDIENDO DE LA RAZA
            razaSeleccionada?.let { raceSelected ->
                when (raceSelected) {

                    Race.HUMANO -> {
                        Text("Armas Disponibles:", color = Color(0xFF2A1F0F),
                            modifier = Modifier.background(panel, RoundedCornerShape(10.dp)).padding(6.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(WeaponType.ESCOPETA, WeaponType.RIFLE).forEach { arma ->
                                FilterChip(
                                    selected = armaSeleccionada == arma,
                                    onClick = { armaSeleccionada = arma },
                                    label = {
                                        Text(arma.name, color = Color(0xFF2A1F0F),
                                            modifier = Modifier.background(panel, RoundedCornerShape(8.dp)).padding(4.dp))
                                    }
                                )
                            }
                        }
                    }

                    Race.ELFO -> {
                        Text("Elemento mágico:", color = Color(0xFF2A1F0F),
                            modifier = Modifier.background(panel, RoundedCornerShape(10.dp)).padding(6.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            ElementoMagico.values().forEach { elem ->
                                FilterChip(
                                    selected = elementoSeleccionado == elem,
                                    onClick = { elementoSeleccionado = elem },
                                    label = {
                                        Text(elem.name, color = Color(0xFF2A1F0F),
                                            modifier = Modifier.background(panel, RoundedCornerShape(8.dp)).padding(4.dp))
                                    }
                                )
                            }
                        }
                        armaSeleccionada = WeaponType.BACULO
                    }

                    Race.ORCO -> {
                        Text("Escoja arma:", color = Color(0xFF2A1F0F),
                            modifier = Modifier.background(panel, RoundedCornerShape(10.dp)).padding(6.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(WeaponType.HACHA, WeaponType.MARTILLO).forEach { arma ->
                                FilterChip(
                                    selected = armaSeleccionada == arma,
                                    onClick = { armaSeleccionada = arma },
                                    label = {
                                        Text(arma.name, color = Color(0xFF2A1F0F),
                                            modifier = Modifier.background(panel, RoundedCornerShape(8.dp)).padding(4.dp))
                                    }
                                )
                            }
                        }
                    }

                    Race.BESTIA -> {
                        Text("Ataque primario:", color = Color(0xFF2A1F0F),
                            modifier = Modifier.background(panel, RoundedCornerShape(10.dp)).padding(6.dp))

                        FlowRow(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(WeaponType.PUNIOS, WeaponType.ESPADA).forEach { arma ->
                                FilterChip(
                                    selected = armaSeleccionada == arma,
                                    onClick = { armaSeleccionada = arma },
                                    label = {
                                        Text(arma.name, color = Color(0xFF2A1F0F),
                                            modifier = Modifier.background(panel, RoundedCornerShape(8.dp)).padding(4.dp))
                                    }
                                )
                            }
                        }
                    }
                }
            }


            // 🔥 BOTÓN CONTINUAR
            Button(
                onClick = {
                    val raza = razaSeleccionada ?: return@Button
                    onJugadorConfigurado(
                        jugadorActual,
                        raza,
                        armaSeleccionada,
                        elementoSeleccionado,
                        jugadorActual == 2
                    )
                    if (jugadorActual == 1) {
                        jugadorActual = 2
                        razaSeleccionada = null
                        armaSeleccionada = null
                        elementoSeleccionado = null
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3A5BA0))
            ) {
                Text("Continuar")
            }
        }
    }
}
