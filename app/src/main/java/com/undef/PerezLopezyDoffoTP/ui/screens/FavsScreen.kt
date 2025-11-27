package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import android.widget.Toast
import com.undef.PerezLopezyDoffoTP.ui.components.EmprendimientoItem
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.FavoriteAlertItem
import com.undef.PerezLopezyDoffoTP.ui.viewModels.FavsViewModel
import com.undef.PerezLopezyDoffoTP.utils.NotificationHelper

@Composable
fun FavsScreen (navController: NavController){
    val viewModel: FavsViewModel = viewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Favs(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                viewModel = viewModel
            )
        }

    }
}

@Composable
fun Favs(modifier: Modifier, navController: NavController, viewModel: FavsViewModel){
    val favorites by viewModel.favoriteItems.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Favoritos",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            if(favorites.isEmpty()){
                Text(
                    text = "Lista de Favoritos Vacía",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }
        items(favorites) { item ->
            FavoriteAlertCard(
                item = item,
                onNavigateToDetail = { emprendimientoId ->
                    navController.navigate(
                        Screen.EmprendimientoDetail.route.replace(
                            "{emprendimientoId}",
                            emprendimientoId.toString()
                        )
                    )
                },
                onAlertChanged = { enabled ->
                    viewModel.setAlertFor(item.emprendimiento.emprendedor.id, enabled)
                    if (enabled) {
                        NotificationHelper.showActivatedNotification(context)
                    }
                    val message = if (enabled) {
                        "Alertas activadas para ${item.emprendimiento.emprendedor.name}"
                    } else {
                        "Alertas desactivadas para ${item.emprendimiento.emprendedor.name}"
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
private fun FavoriteAlertCard(
    item: FavoriteAlertItem,
    onNavigateToDetail: (Int) -> Unit,
    onAlertChanged: (Boolean) -> Unit
) {
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        EmprendimientoItem(item.emprendimiento, onClick = onNavigateToDetail)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Alertas de ${item.emprendimiento.emprendedor.name}",
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "Recibí una alerta cuando publique algo nuevo.",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            Switch(
                checked = item.alertsEnabled,
                onCheckedChange = onAlertChanged
            )
        }
    }
}
