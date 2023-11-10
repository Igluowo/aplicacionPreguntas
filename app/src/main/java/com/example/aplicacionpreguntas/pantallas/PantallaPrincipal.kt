package com.example.aplicacionpreguntas.pantallas

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.aplicacionpreguntas.Navegacion

@Composable
fun PantallaPrincipal(navController: NavController) {
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Seleccione lo que quiera hacer", textAlign = TextAlign.Center, modifier = Modifier.padding(10.dp))
        Button(onClick = { navController.navigate(route = Navegacion.PantallaPractica.ruta) }) {
            Text(text = "Iniciar modo practica")
        }
        Button(onClick = { navController.navigate(route = Navegacion.PantallaExamen.ruta) }) {
            Text(text = "Iniciar modo Examen")
        }
        Button(onClick = { navController.navigate(route = Navegacion.PantallaSubir.ruta) }) {
            Text(text = "Subir pregunta")
        }
    }
}