package com.undef.PerezLopezyDoffoTP.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.undef.PerezLopezyDoffoTP.data.model.Emprendimiento
import com.undef.PerezLopezyDoffoTP.ui.viewModels.HomeViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .padding(16.dp)) {
        Home(modifier = Modifier.fillMaxWidth(), homeViewModel)
    }
}

@Composable
fun Home(modifier: Modifier, homeViewModel: HomeViewModel) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val emprendimientos by homeViewModel.emprendimientos.observeAsState(listOf())

    Column(modifier = modifier) {
        SearchBar(searchQuery) { query ->
            searchQuery = query
            homeViewModel.searchEmprendimientos(query.text)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Emprendimientoos populares",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EmprendimientoList(emprendimientos)
    }
}

@Composable
fun Spacer(modifier: Modifier) {
    Column(modifier = modifier) {}
}

@Composable
fun SearchBar(searchQuery: TextFieldValue, onSearchQueryChanged: (TextFieldValue) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f), MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChanged,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onSurface)
            )
        }
    }
}

@Composable
fun EmprendimientoList(emprendimientos: List<Emprendimiento>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(emprendimientos) { emp ->
            EmprendimientoItem(emp)
        }
    }
}

@Composable
fun EmprendimientoItem(emprendimiento: Emprendimiento) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(emprendimiento.image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            // Difuminado negro sobre la imagen
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.7f)
                            ),
                            startY = 0f,
                            endY = 300f
                        )
                    )
            )
            // Texto sobre la imagen difuminada
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = emprendimiento.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White
                )
                Text(
                    text = "Capacity: ${emprendimiento.tel}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Text(
                    text = emprendimiento.location,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
                Text(
                    text = "Organizer: ${emprendimiento.website}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }
}