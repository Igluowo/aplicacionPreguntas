package com.example.aplicacionpreguntas

sealed class Navegacion(val ruta: String) {
    object PantallaPrincipal: Navegacion("PantallaPrincipal")
    object PantallaPractica: Navegacion("PantallaPractica")
    object PantallaExamen: Navegacion("PantallaExamen")
    object PantallaSubir: Navegacion("PantallaSubir")
}
