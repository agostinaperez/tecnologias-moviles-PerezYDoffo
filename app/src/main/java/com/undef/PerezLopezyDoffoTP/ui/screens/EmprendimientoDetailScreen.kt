package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
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
import com.undef.PerezLopezyDoffoTP.repository.EmprendimientoRepository
import com.undef.PerezLopezyDoffoTP.ui.components.BackBar
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen


@Composable
fun EmprendimientoDetailScreen(emprendimientoId: Int, navController: NavHostController) {
    BackBar(navController){ paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            EmprendimientoDetail(emprendimientoId = emprendimientoId, navController)
        }
    }
}

@Composable
fun EmprendimientoDetail(emprendimientoId: Int, navController: NavHostController) {
    val emprendimiento = EmprendimientoRepository.getEmprendimientoById(emprendimientoId)

    if (emprendimiento == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "No encontramos este emprendimiento",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Volvé a la pantalla anterior e intentá nuevamente.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        return
    }

    val emprendedor = emprendimiento.emprendedor
    val otrosProductos = EmprendimientoRepository.getEmprendimientosDelEmprendedor(
        emprendedor.id,
        excludeEmprendimientoId = emprendimiento.id
    )
    var isFav by remember(emprendimiento.id) { mutableStateOf(emprendimiento.isFav) }

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
        Text(
            text = emprendimiento.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Detalles del emprendimiento
        Text(text = "Categoría: ${emprendimiento.category}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Emprendedor/a",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = emprendedor.name,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = emprendedor.bio,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Ubicación: ${emprendedor.location}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Web: ${emprendedor.website}",
            style = MaterialTheme.typography.bodyMedium
        )

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
            onClick = {
                EmprendimientoRepository.setFav(emprendimientoId)
                isFav = !isFav
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
        ) {
            if (!isFav) {
                Text(
                    text = "Agregar a Favoritos",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary)
                )
            } else{
                Text(
                    text = "Quitar de Favoritos",
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Otros productos de ${emprendedor.name}",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (otrosProductos.isEmpty()) {
            Text(
                text = "Este emprendedor aún no publicó más productos.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(otrosProductos) { producto ->
                    RelatedEmprendimientoCard(
                        emprendimiento = producto,
                        onClick = {
                            navController.navigate(
                                Screen.EmprendimientoDetail.route.replace(
                                    "{emprendimientoId}",
                                    producto.id.toString()
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RelatedEmprendimientoCard(
    emprendimiento: Emprendimiento,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(180.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(emprendimiento.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = emprendimiento.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = emprendimiento.category,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
    }
}
