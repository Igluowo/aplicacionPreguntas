package com.example.aplicacionpreguntas.pantallas

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposableTarget
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaSubir(navController: NavController, context: Context) {
    val rutaFichero = context.filesDir
    var desplegado by remember { mutableStateOf(false) }
    var opcion by remember { mutableStateOf("True") }
    val archivo = File(rutaFichero, "pregunta.txt")
    var contenido = ""
    if(!archivo.exists()) {
            archivo.createNewFile()
    }else{
        contenido = archivo.readText();
    }
    var pregunta by remember { mutableStateOf("") }
    Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        TextField(value = pregunta, onValueChange = { pregunta = it }, label = { Text(text = "Pregunta")}, modifier = Modifier.padding(20.dp))
        ExposedDropdownMenuBox(expanded = desplegado, onExpandedChange = { desplegado = true }) {
                TextField(value = opcion,
                    onValueChange = {}, label = { Text(text = "Resultado") }, readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = desplegado)},
                    modifier = Modifier.menuAnchor())
            DropdownMenu(expanded = desplegado, onDismissRequest = { desplegado = false }, modifier = Modifier.width(TextFieldDefaults.MinWidth)) {
                DropdownMenuItem(text = { Text(text = "True") }, onClick = { opcion = "True"; desplegado = false })
                DropdownMenuItem(text = { Text(text = "False") }, onClick = { opcion = "False"; desplegado = false })
            }
        }
        Button(onClick = {
            val bw = BufferedWriter(FileWriter(archivo));
            bw.write("$contenido$pregunta, $opcion\n")
                         bw.close()
            navController.popBackStack()
                         Toast.makeText(context, "Tu pregunta se ha guardado con éxito", Toast.LENGTH_SHORT).show()}, Modifier.padding(25.dp)) {
            Text(text = "Subir pregunta")
        }
    }
}