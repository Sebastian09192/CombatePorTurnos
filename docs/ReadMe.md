# ⚔ Proyecto Final — Videojuego de Combate por Turnos para Android  
### Desarrollo de Aplicaciones Móviles I — ITI-623  
### Universidad Técnica Nacional • Ingeniería en Tecnologías de la Información  

---

## 📌 1. Nombre del Proyecto
**Combate Por Turnos — Fantasy Battle Arena**

---

## 📌 2. Descripción General del Juego
Este proyecto consiste en un videojuego móvil de combate por turnos para 2 jugadores en modo local  
("pass-and-play"), desarrollado para Android usando **Kotlin + Jetpack Compose**.

Cada jugador:
- Ingresa su nombre  
- Selecciona una raza  
- Elige un arma o elemento mágico  
- Combate alternando turnos hasta dejar la vida del oponente en 0  

El juego incluye:
- Razas con ventajas y desventajas  
- Distancia de combate (avance y retroceso)  
- Daños únicos por arma  
- Sanaciones especiales según raza  
- Historial de estadísticas persistentes  
- Imágenes del personaje según raza/arma/elemento  

---

## 📌 3. Autor
**Sebastián Alpízar Arce**  
Rol: Programador — Diseño, lógica del juego, interfaz, integración y recursos visuales.
**Cristian Rojas Morales** 
Rol: Revisor — Supervisión general del juego, evaluación de la jugabilidad, corrección de detalles y ajustes visuales menores para mejorar la presentación gráfica. 
---

## 📌 4. Cómo instalar el APK
1. Descargar el archivo **APK** desde el repositorio (carpeta `/apk/`).  
2. Copiarlo al teléfono o descargarlo directamente desde el navegador.  
3. Activar “Instalar aplicaciones de orígenes desconocidos”.  
4. Ejecutar el APK y presionar *Instalar*.  

---

## 📌 5. Requisitos para ejecutar el juego
- Android **7.0 o superior** (API 24+)  
- 200MB de almacenamiento libre  
- No requiere internet  
- Funciona en emulador y en dispositivos reales  

---

## 📌 6. Instrucciones del Juego
### 🧙 Selección inicial
1. Cada jugador ingresa su nombre.  
2. Luego elige su raza y su arma/elemento.  
3. Se inicia la batalla automáticamente cuando ambos están configurados.

---

### ⚔ Razas y mecánicas implementadas

#### 🧍 HUMANOS
- Armas: Escopeta / Rifle Francotirador  
- Daño base: 1–5  
- Rifle a distancia → daño ampliado (10–20)  
- Sanación: comen → recuperan 40%–49%  

#### 🧝‍♂️ ELFOS (báculo mágico)
- Fuego → daño aumentado  
- Tierra → bono leve de ataque + mayor acierto  
- Aire → daño normal + posibilidad de evasión  
- Agua → +vida base (115) + mejor curación  
- Sanaciones:  
  - Elfos comunes → 65%  
  - Agua → 75%–90%  

#### 🪓 ORCOS
- Hacha → daño 1–5 + sangrado (–3 por 2 turnos)  
- Martillo → daño 2–7  
- Pociones → cura 25%–45% + extra 5%–25% en siguiente turno  

#### 🐺 BESTIAS
- Puños → daño fijo 20–30 pero pierden 10 vida al atacar  
- Espada → daño aleatorio 1–10  
- Dormir → sana 50% de la vida perdida  

---

### 🎯 Distancia y movimiento
- 0 = Cerca  
- 1 = Medio  
- 2 = Lejos (los dos inician aquí)  
- Avanzar / Retroceder afecta capacidad de ataque:  
    - Solo el rifle y la magia aire pueden atacar desde lejos.  

---

### 🏆 Condición de Victoria
Gana el jugador que deje la vida del oponente en 0 o menos.  
El juego muestra un resumen con:
- Ganador  
- Estadísticas del combate  
- Raza y arma utilizada  
- Vida restante  

Las estadísticas globales también se actualizan.

---

## 📌 7. Tecnologías utilizadas
- **Kotlin**  
- **Jetpack Compose**  
- **MVVM (Model–View–ViewModel)**  
- **Navegación Compose**  
- **SharedPreferences / JSON interno (persistencia de estadísticas)**  
- **Android Studio Giraffe**  

---

## 📌 8. Arquitectura del Proyecto
app/
└── com.example.combateporturnos
├── data/
│ └── StatsManager.kt # Persistencia
├── model/
│ ├── Player.kt
│ ├── Race.kt
│ ├── WeaponType.kt
│ ├── ElementoMagico.kt
│ └── GameState.kt
├── ui/
│ ├── CombateApp.kt # Navegación
│ ├── GameViewModel.kt # Lógica principal
│ └── screens/
│ ├── StartScreen.kt
│ ├── RaceSelectionScreen.kt
│ ├── BattleScreen.kt
│ ├── SummaryScreen.kt
│ └── StatsScreen.kt
└── resources/
├── Imagenes de razas+armas
├── Fondos
└── Iconos

---

## 📌 9. Licencia
Uso académico exclusivo para la UTN.  
Prohibido distribuir el APK fuera del contexto del curso.