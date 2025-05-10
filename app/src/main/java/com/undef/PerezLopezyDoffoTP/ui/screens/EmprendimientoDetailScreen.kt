package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar


@Composable
fun EmprendimientoDetailScreen(emprendimientoId: Number, navController: NavHostController) {
    BackBar(navController){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EmprendimientoDetail(emprendimientoId = emprendimientoId)
        }
    }
}

@Composable
fun EmprendimientoDetail(emprendimientoId: Number) {
    val emprendimiento: Emprendimiento = Emprendimiento.getEmprendimientoById(emprendimientoId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Imagen destacada
        Image(
            painter = rememberAsyncImagePainter(emprendimiento.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .clip(RoundedCornerShape(12.dp))
                .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nombre del emprendimiento
        Text(
            text = emprendimiento.name,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Detalles del emprendimiento
        Text(text = "Producto: ${emprendimiento.producto}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Ubicación: ${emprendimiento.location}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Página web del emprendedor: ${emprendimiento.website}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para comprar
        Button(
            onClick = { /* Lógica para comprar */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(contentColor = contentColorFor(MaterialTheme.colorScheme.primary))
        ) {
            Text(text = "Comunicarse con el vendedor", style = MaterialTheme.typography.bodyLarge.copy(color = Color.White))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para agregar a favoritos
        OutlinedButton(
            onClick = { /* Lógica para agregar a favoritos */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Agregar a Favoritos", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary))
        }
    }
}