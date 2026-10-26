package com.example.appcorpaliv_grupo11.ui.screens.quienesSomos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.appcorpaliv_grupo11.ui.screens.leyInclusion.obtenerWindowWidthSizeClass
import androidx.compose.foundation.lazy.items

@Composable
fun QuienesSomosScreen(
    windowSizeClass: WindowWidthSizeClass = obtenerWindowWidthSizeClass(),
    onContactoClick: () -> Unit = {}
) {
    when (windowSizeClass) {
        WindowWidthSizeClass.Compact -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Medium -> QuienesSomosScreenMediana(onContactoClick = onContactoClick)
        WindowWidthSizeClass.Expanded -> QuienesSomosScreenExpandida(onContactoClick = onContactoClick)
        else -> QuienesSomosScreenCompacta(onContactoClick = onContactoClick)
    }
}

@Composable
fun HeaderSeccion() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Alba Lab de Corpaliv",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFE65100),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Somos una red socio-laboral dedicada a promover la inclusión de jóvenes y adultos con discapacidad múltiple.",
            fontSize = 15.sp,
            color = Color.DarkGray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CorpalivCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Somos parte de Corpaliv",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333333)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Institución que se dedica hace 30 años a la educación y capacitación por y para personas con discapacidad múltiple y trabajamos de manera colaborativa con diversos actores de la sociedad para crear un entorno inclusivo y de apoyo.",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MisionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "Misión",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE65100)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nuestra misión es poner a la persona en el centro de nuestras acciones, enfocándonos en su bienestar integral. Buscamos generar conciencia sobre quiénes somos y nuestro entorno, incentivando a cada persona a descubrir su propia forma, su voz y su luz. Creemos firmemente que todos tenemos una luz que puede brillar.",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}

data class MiembroEquipo(val nombre: String, val cargo: String)

@Composable
fun EquipoSeccion() {
    val equipo = listOf(
        MiembroEquipo("Carla Valdés", "Terapeuta Ocupacional"),
        MiembroEquipo("Eva Mora Ortega", "Terapeuta Ocupacional"),
        MiembroEquipo("Macarena Hasche Henríquez", "Directora Red Sociolaboral")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nuestro Equipo",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(equipo) { miembro ->
                TarjetaMiembro(miembro = miembro)
            }
        }
    }
}

@Composable
private fun TarjetaMiembro(miembro: MiembroEquipo) {
    Card(
        modifier = Modifier.width(180.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Placeholder avatar circular
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = miembro.nombre,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = miembro.cargo,
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}