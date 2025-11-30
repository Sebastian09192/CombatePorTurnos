# ⚔️ Videojuego de Combate por Turnos – Android (Kotlin + Jetpack Compose)

Proyecto final del curso **ITI-623 – Desarrollo de Aplicaciones para Dispositivos Móviles I**  
**Universidad Técnica Nacional – Ingeniería en Tecnologías de la Información**

---

## 📌 1. Descripción General

Este proyecto consiste en un **videojuego de combate de fantasía por turnos**, desarrollado para Android utilizando **Kotlin** y **Jetpack Compose**.

El juego implementa:

- Lógica de turnos completa  
- Sistema de razas, armas y habilidades especiales  
- Distancia de combate (cercana, media, larga)  
- Avanzar, retroceder, atacar, sanar  
- Persistencia de datos mediante **SharedPreferences** (estadísticas e historial)  
- Interfaz gráfica con imágenes representativas por turno  
- Navegación entre pantallas con Navigation Compose

El APK es totalmente funcional y ejecutable tanto en emulador como en dispositivo Android real.

---

## 👥 2. Integrantes

- **Sebastián Alpízar Arce** – Programación general, UI, lógica del juego, persistencia.

---

## 🧰 3. Tecnologías Utilizadas

- **Android Studio Ladybug**  
- **Kotlin**  
- **Jetpack Compose**  
- **Navigation Compose**  
- **SharedPreferences (persistencia local)**  
- **ViewModel + State Management**  
- **Material Design 3**  

---

## 🕹️ 4. Instrucciones de Juego

### 4.1 Flujo inicial
1. Ingresar nombres de Jugador 1 y Jugador 2.
2. Seleccionar raza y arma/habilidad.
3. Iniciar el combate.

### 4.2 Razas disponibles
- **Humano** → armas de fuego (escopeta, rifle).  
- **Elfo** → magia elemental (fuego, aire, tierra, agua).  
- **Orco** → armas pesadas (hacha, martillo).  
- **Bestia** → puños o espada.  

Cada raza tiene **ventajas, desventajas, daños únicos y tipos de sanación especiales**.

### 4.3 Combate
Cada jugador puede:
- **Avanzar**
- **Retroceder**
- **Atacar**
- **Sanar**

El ataque y sanación dependen de raza/arma/elemento y de la **distancia**.

### 4.4 Condición de victoria
Gana quien reduce la vida del oponente a 0.  
Se guardan:
- Victorias  
- Derrotas  
- Empates  
- Historial de partidas  

---

## 💾 5. Persistencia de Datos

Se utiliza **SharedPreferences** para guardar:

- Estadísticas de cada jugador  
  - Partidas ganadas  
  - Perdidas  
  - Empatadas  
- Historial de partidas  
  - Nombre vs nombre  
  - Ganador  
  - Fecha/hora  
- Última configuración de combate

Esto garantiza que los datos **se mantienen incluso si se cierra la aplicación**.

---

## 🧱 6. Arquitectura del Proyecto
/data
StatsManager.kt -> manejo de persistencia

/model
Player.kt -> datos del jugador
Race.kt -> enums de razas
WeaponType.kt -> enums de armas
ElementoMagico.kt -> enums de magia
GameState.kt -> wrapper de estados (opcional)

/ui
CombateApp.kt -> navegación principal

/ui/screens
StartScreen.kt
RaceSelectionScreen.kt
BattleScreen.kt
SummaryScreen.kt
StatsScreen.kt -> historial y estadísticas

Arquitectura basada en:
✔ MVVM  
✔ State Hoisting  
✔ Unidirectional Flow  
