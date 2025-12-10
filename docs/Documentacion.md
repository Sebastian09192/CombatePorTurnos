# 📘 Documentación Técnica del Videojuego de Combate por Turnos

## 1. Introducción
Este documento describe la arquitectura, lógica interna, reglas implementadas y estructura funcional del videojuego **Combate Por Turnos**, desarrollado en Kotlin utilizando Jetpack Compose.

---

## 2. Reglas del Juego Implementadas

### ✔ Turnos
- Turnos alternos entre Jugador 1 y Jugador 2.
- Un turno permite: atacar, sanar, avanzar o retroceder.
- El turno termina después de realizar una acción.

---

### ✔ Distancia
- Distancia entre jugadores representada como un entero:  
  - 0 = Cuerpo a cuerpo  
  - 1 = Media  
  - 2 = Larga distancia (posición inicial)
- Algunas armas solo funcionan en 0.  
- Rifle y Aire pueden atacar a 2.

---

### ✔ Daños por raza
Cada raza tiene su propia lógica:

#### PJ Humanos:
- Armas: escopeta y rifle.  
- Daño base 1–5.  
- Rifle a distancia → daño especial 10–20.  
- Sanación: comen (40%–49%).

#### PJ Elfos:
- Arma: báculo.  
- Elementos: fuego, tierra, aire, agua.  
- Agua → vida base 115.
- Sanación:
  - Comunes → 65%
  - Agua → 75%–90%

#### PJ Orcos:
- Hacha → daño + sangrado  
- Martillo → daño 2–7  
- Pociones → cura dos fases

#### PJ Bestias:
- Puños → daño 20–30 pero pierden 10  
- Espada → daño 1–10  
- Sanación → dormir (50%)

---

### ✔ Condición de victoria
- La batalla termina cuando alguno queda en 0 vida.
- Se muestra una pantalla de resumen.
- Se actualizan estadísticas globales.

---

## 3. Lógica de Turnos (Detalle Técnico)
El **GameViewModel** mantiene todo el estado del juego:
turnoJugadorId = 1 o 2
distancia = 0, 1, 2
jugador1: Player
jugador2: Player


### Flujo:
1. Usuario realiza una acción (atacar/sanar/avanzar/retroceder).  
2. GameViewModel actualiza los valores.  
3. Se invierte el turno (`turnoJugadorId = 3 - turnoJugadorId`).  
4. Se agrega texto al log.  
5. Si la vida del enemigo ≤ 0 → fin de partida.  

---

## 4. Persistencia del Juego
Se utiliza **SharedPreferences** para almacenar:

- Total de partidas ganadas, perdidas y empatadas.  
- Últimos personajes utilizados.  
- Historial básico.

Archivo manejado por `StatsManager.kt`.

Valores típicos:
j1_ganadas = 4
j1_perdidas = 7
j2_ganadas = 6
ultima_partida = "Ganó Jugador 1 (Elfo Aire)"


---

## 5. Arquitectura del Sistema (MVVM)

### Modelo (model/)
Contiene todas las clases de datos:
- Player  
- Race  
- WeaponType  
- ElementoMagico  
- GameState  

### Vista (ui/screens/)
Pantallas:
- StartScreen  
- RaceSelectionScreen  
- BattleScreen  
- SummaryScreen  
- StatsScreen  

### ViewModel
`GameViewModel.kt` controla:
- Turnos  
- Daños  
- Distancia  
- Sanación  
- Actualización de estadísticas  
- Log del combate  

---

## 6. Diagrama Simplificado (Flowchart)

┌────────────────┐
│ Pantalla Inicio │
└───────┬────────┘
▼
┌──────────────────────────┐
│ Selección de Jugador 1 │
└───────┬──────────────────┘
▼
┌──────────────────────────┐
│ Selección de Jugador 2 │
└───────┬──────────────────┘
▼
┌──────────────────────────┐
│ Batalla │
│ - Atacar │
│ - Sanar │
│ - Avanzar / Retroceder │
└───────┬──────────────────┘
▼
┌──────────────────────────┐
│ ¿Vida <= 0? │───Sí──→ Resumen y Guardar Stats
└──────────────┬──────────┘
│No
▼
Continuar turnos

---

## 7. Estructura del Proyecto
com.example.combateporturnos
├── data
│ └── StatsManager.kt
├── model
│ ├── Player.kt
│ ├── Race.kt
│ ├── WeaponType.kt
│ ├── ElementoMagico.kt
│ └── GameState.kt
├── ui
│ ├── CombateApp.kt
│ ├── GameViewModel.kt
│ └── screens/
│ ├── StartScreen.kt
│ ├── RaceSelectionScreen.kt
│ ├── BattleScreen.kt
│ ├── SummaryScreen.kt
│ └── StatsScreen.kt
└── res/
├── drawable/
├── values/
└── fondos, sprites, iconos


---

## 8. Conclusión Técnica
El proyecto cumple con:
- Lógica completa de combate  
- Funcionamiento alternado por turnos  
- Persistencia de estadísticas  
- Arquitectura MVVM clara  
- Navegación fluida entre pantallas  
- Imágenes dinámicas según personaje  

El sistema es extensible y permite agregar nuevas razas, armas o modos de juego.


