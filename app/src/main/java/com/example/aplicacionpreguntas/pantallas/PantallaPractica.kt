package com.example.aplicacionpreguntas.pantallas

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun PantallaPractica(navController: NavController, context: Context) {
    PantallaPreguntas(context = context, false)
}