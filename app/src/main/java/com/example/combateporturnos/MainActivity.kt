package com.example.combateporturnos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.combateporturnos.ui.CombateApp
import com.example.combateporturnos.ui.theme.CombatePorTurnosTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CombatePorTurnosTheme {
                CombateApp()
            }
        }
    }
}
