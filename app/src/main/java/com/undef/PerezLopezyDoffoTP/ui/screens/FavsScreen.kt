package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.ui.components.EmprendimientoItem
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.FavsViewModel

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
    val favorites by viewModel.favorites.collectAsStateWithLifecycle()
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
        items(favorites) { emprendimiento ->
            EmprendimientoItem(emprendimiento) { emprendimientoId ->
                navController.navigate(
                    Screen.EmprendimientoDetail.route.replace("{emprendimientoId}", emprendimientoId.toString())
                )
            }
        }
    }
}
