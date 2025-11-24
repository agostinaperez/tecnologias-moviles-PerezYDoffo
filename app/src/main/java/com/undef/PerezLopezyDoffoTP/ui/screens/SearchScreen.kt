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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.components.SearchBar
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.SearchViewModel
import com.undef.PerezLopezyDoffoTP.ui.components.EmprendimientoItem


@Composable
fun SearchScreen(navController: NavController, title: String) {
    val searchViewModel: SearchViewModel = viewModel()
    LaunchedEffect(title) {
        searchViewModel.updateQuery(title)
    }
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Search(
                modifier = Modifier.fillMaxWidth(),
                navController = navController,
                searchViewModel = searchViewModel
            )
        }

    }
}

@Composable
fun Search(
    modifier: Modifier,
    navController: NavController,
    searchViewModel: SearchViewModel
) {
    val currentQuery by searchViewModel.searchQuery.collectAsStateWithLifecycle()
    var searchQuery by remember { mutableStateOf(TextFieldValue(currentQuery)) }
    val filteredEmprendimientos by searchViewModel.emprendimientos.collectAsStateWithLifecycle()

    LaunchedEffect(currentQuery) {
        if (searchQuery.text != currentQuery) {
            searchQuery = TextFieldValue(
                text = currentQuery,
                selection = TextRange(currentQuery.length)
            )
        }
    }

    LazyColumn(modifier = modifier) {
        item {
            SearchBar(searchQuery) { query ->
                searchQuery = query
                searchViewModel.updateQuery(query.text)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Resultado de tu busqueda",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(filteredEmprendimientos) { emprendimiento ->
            EmprendimientoItem(emprendimiento) { selectedEmprendimiento ->
                navController.navigate(Screen.EmprendimientoDetail.route.replace("{emprendimientoId}", selectedEmprendimiento.toString()))
            }
        }
    }
}
