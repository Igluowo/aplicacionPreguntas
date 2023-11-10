package com.example.aplicacionpreguntas.pantallas

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.aplicacionpreguntas.Pregunta
import com.example.aplicacionpreguntas.R
import java.io.File
import java.util.Random

@Composable
fun PantallaPreguntas(context : Context, examen: Boolean) {
    var listaPreguntas: MutableList<Pregunta>
    if (examen) {
        listaPreguntas = creacionLista(context = context)
    }else{
        listaPreguntas = creacionLista(context = context).shuffled().toMutableList()
    }
    var opcion by remember { mutableStateOf("") }
    var respuestaUsuario by rememberSaveable { mutableStateOf(false) }
    var botonesActivos by rememberSaveable { mutableStateOf(true) }
    var cerrar by remember { mutableStateOf(false) }
    var respondido by rememberSaveable { mutableStateOf(false) }
    var contador by rememberSaveable { mutableIntStateOf(0) }
    var preguntaAnterior = rememberSaveable { mutableListOf<Pregunta>()}
    var mostrar by rememberSaveable { mutableStateOf(false) }
    var respuestasCorrectas by rememberSaveable { mutableIntStateOf(0) }
    var pregunta by rememberSaveable { mutableStateOf(listaPreguntas[contador].pregunta) }
    var imagenId by rememberSaveable { mutableIntStateOf(listaPreguntas[contador].imagenId) }
    var respuesta by rememberSaveable { mutableStateOf(listaPreguntas[contador].respuesta) }
    var configuracion = LocalConfiguration.current
    Column(verticalArrangement = Arrangement.SpaceBetween
        ,horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = pregunta, Modifier.padding(20.dp))
        if (configuracion.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            Image(painter = painterResource(id = imagenId), contentDescription = "")
        }
        Box {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(opcion)
                if (respondido || contador == listaPreguntas.size) {
                    comprobarRespuesta(respuesta, respuestaUsuario)
                }
                Row() {
                    Button(onClick = {
                        if (!respondido) {
                            respondido = true; respuestaUsuario = true; botonesActivos = false;
                            respuestasCorrectas = sumarRespuesta(respuestasCorrectas, respuestaUsuario, respuesta)
                            if (examen) {
                                if (contador == listaPreguntas.size - 1) {
                                    mostrar = true
                                }else{
                                    contador++
                                    pregunta = listaPreguntas[contador].pregunta
                                    imagenId = listaPreguntas[contador].imagenId
                                    respuesta = listaPreguntas[contador].respuesta
                                    botonesActivos = true
                                    respondido = false
                                }
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        enabled = botonesActivos
                    ) {
                        Text(text = "Verdadero")
                    }
                    Button(onClick = {
                        if (!respondido) {
                            respondido = true; respuestaUsuario = false; botonesActivos = false
                            respuestasCorrectas = sumarRespuesta(respuestasCorrectas, respuestaUsuario, respuesta)
                            if (examen) {
                                if (contador == listaPreguntas.size - 1) {
                                    mostrar = true
                                } else {
                                    contador++
                                    pregunta = listaPreguntas[contador].pregunta
                                    imagenId = listaPreguntas[contador].imagenId
                                    respuesta = listaPreguntas[contador].respuesta
                                    botonesActivos = true
                                    respondido = false
                                }
                            }
                        }
                    }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        enabled = botonesActivos
                    ) {
                        Text(text = "Falso")
                    }
                }
                if(!examen) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Button(
                            onClick = {
                                if (contador <= 0) {
                                    Toast.makeText(context, "No hay preguntas previas", Toast.LENGTH_SHORT).show()
                                }else{
                                    pregunta = preguntaAnterior[contador - 1].pregunta
                                    imagenId = preguntaAnterior[contador - 1].imagenId
                                    respuesta = preguntaAnterior[contador - 1].respuesta
                                    contador--
                                }
                            },
                            shape = RectangleShape, modifier = Modifier.padding(12.dp, 0.dp),

                            ) {
                            Icon(painter = painterResource(R.drawable.flechaizquierda), contentDescription = "")
                            Text(text = " Anterior")
                        }
                        Button(
                            onClick = {
                                if (respondido) {
                                    if (contador >= listaPreguntas.size - 1) {
                                        contador = 0
                                        pregunta = listaPreguntas[contador].pregunta
                                        imagenId = listaPreguntas[contador].imagenId
                                        respuesta = listaPreguntas[contador].respuesta
                                        botonesActivos = true
                                        respondido = false
                                    }else {
                                        preguntaAnterior.add(listaPreguntas[contador])
                                        contador++
                                        pregunta = listaPreguntas[contador].pregunta
                                        imagenId = listaPreguntas[contador].imagenId
                                        respuesta = listaPreguntas[contador].respuesta
                                        botonesActivos = true
                                        respondido = false
                                        println(preguntaAnterior.size)
                                    }
                                }else{
                                    Toast.makeText(context, "Debe responder a la pregunta antes de avanzar", Toast.LENGTH_SHORT).show()
                                }
                            },
                            shape = RectangleShape, modifier = Modifier.padding(12.dp, 0.dp)
                        ) {
                            Text(text = "Siguiente ")
                            Icon(painter = painterResource(R.drawable.flechaderecha), contentDescription = "")
                        }
                    }
                }
            }
        }
    }
    preguntaAnterior.forEach { println(it.pregunta) }
    abrirDialog(mostrar , { cerrar = true }, respuestasCorrectas, listaPreguntas.size)
    if (cerrar) {
        contador = 0
        respuestasCorrectas = 0
        mostrar = false
        cerrar = false
        pregunta = listaPreguntas[contador].pregunta
        imagenId = listaPreguntas[contador].imagenId
        respuesta = listaPreguntas[contador].respuesta
        botonesActivos = true
        respondido = false
    }
}

@Composable
fun abrirDialog(mostrar : Boolean, onDismiss:() -> Unit, respuestasCorrectas: Int, nPreguntas: Int) {
    if (mostrar) {
        AlertDialog(onDismissRequest = { },
            confirmButton = { TextButton(onClick = { onDismiss() }) { Text(text = "Volver a jugar")} },
            text = { if (respuestasCorrectas == nPreguntas) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(id = R.drawable.iq1000), contentDescription = "")
                    Text(text = "WOW, eres un crack. Tienes una inteligencia muy superior.", textAlign = TextAlign.Center)
                }
            }else if (respuestasCorrectas >= (nPreguntas / 2) && respuestasCorrectas < nPreguntas){
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.normal),
                        contentDescription = ""
                    )
                    Text(
                        text = "No está mal, lo haces bien",
                        textAlign = TextAlign.Center
                    )
                }
            }else{
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.iq0),
                        contentDescription = ""
                    )
                    Text(
                        text = "Que malo que eres, tu IQ es menos de -10000",
                        textAlign = TextAlign.Center
                    )
                }
            }}
        )
    }
}

@Composable
fun comprobarRespuesta(respuesta: Boolean, respuestaUsuario :Boolean) {
    if (respuesta == respuestaUsuario) {
        Text(text = "La respuesta es correcta", color = Color.Green)
    }else{
        Text(text = "La respuesta es incorrecta", color = Color.Red)
    }
}

fun sumarRespuesta(respuestasCorrectas: Int, respuestaUsuario: Boolean, respuesta: Boolean): Int {
    var nRespuestas = 0
    if (respuesta == respuestaUsuario) {
        nRespuestas = respuestasCorrectas + 1
    }else{
        nRespuestas = respuestasCorrectas
    }
    return nRespuestas
}

fun creacionLista(context: Context): MutableList<Pregunta> {
    var random = Random()
    val pregunta1 = Pregunta( "¿Queso?", false, R.drawable.queso)
    val pregunta2 = Pregunta( "Venezuela FC ha ganado un mundial", false, R.drawable.escudovenezuela)
    val pregunta3 = Pregunta( "El sol brilla y la luna no", false, R.drawable.rana)
    val pregunta4 = Pregunta("La vaca lola, la vaca lola, tiene cabeza y tiene cola", true, R.drawable.vaca)
    val pregunta5 = Pregunta("¿España?", true, R.drawable.hispano)
    val pregunta6 = Pregunta( "Según un usuario experto en debates en la plataforma " +
            "'X' El color de pelo no natural de las mujeres es una señal para alejarte, " +
            "¿es cierta esta afirmación?", true, R.drawable.x
    )
    val rutaFichero = context.filesDir
    val listaPreguntas = mutableListOf<Pregunta>(pregunta1, pregunta2, pregunta3, pregunta4, pregunta5, pregunta6)
    if (File(rutaFichero, "pregunta.txt").exists()) {
        val archivo = File(rutaFichero, "pregunta.txt")
        var contenido = archivo.readLines()
        for (lineas in contenido) {
            val partes = lineas.split(",")
            val pregunta = partes[0].trim()
            val respuesta = partes[1].trim()
            var respuestaBool = false
            if (respuesta == "True") {
                respuestaBool = true
            }
            listaPreguntas.add(Pregunta(pregunta = pregunta, respuesta = respuestaBool, imagenId = R.drawable.personalizado))
        }
    }
    return listaPreguntas
}