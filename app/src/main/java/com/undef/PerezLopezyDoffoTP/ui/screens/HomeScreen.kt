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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.viewModels.HomeViewModel
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.components.SearchBar
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.components.EmprendimientoItem



@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Home(modifier = Modifier.fillMaxWidth(), homeViewModel, navController)
        }
    }
}

@Composable
fun Home(modifier: Modifier, homeViewModel: HomeViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val emprendimientos by homeViewModel.emprendimientos.observeAsState(listOf())

    LazyColumn(modifier = modifier) {
        item {
            SearchBar(searchQuery) { query ->
                searchQuery = query
                navController.navigate(Screen.Search.route.replace("{title}", query.text))
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Emprendimientos cerca de tu zona",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(emprendimientos) { emprendimiento ->
            EmprendimientoItem(emprendimiento) { selectedEmprendimiento ->
                navController.navigate(Screen.EmprendimientoDetail.route.replace("{emprendimientoId}", selectedEmprendimiento.toString()))
            }
        }
    }
}