package com.example.aplicacionpreguntas

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aplicacionpreguntas.pantallas.PantallaExamen
import com.example.aplicacionpreguntas.pantallas.PantallaPractica
import com.example.aplicacionpreguntas.pantallas.PantallaPrincipal
import com.example.aplicacionpreguntas.pantallas.PantallaSubir


@Composable
fun NavegacionHost(context: Context, volver: Boolean) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Navegacion.PantallaPrincipal.ruta) {
        composable(route = Navegacion.PantallaPrincipal.ruta) {
            PantallaPrincipal(navController)
        }
        composable(route = Navegacion.PantallaPractica.ruta) {
            PantallaPractica(navController, context)
        }
        composable(route = Navegacion.PantallaExamen.ruta) {
            PantallaExamen(navController, context)
        }
        composable(route = Navegacion.PantallaSubir.ruta) {
            PantallaSubir(navController, context)
        }
    }
}