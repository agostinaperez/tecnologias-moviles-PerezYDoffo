package com.undef.PerezLopezyDoffoTP.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.undef.PerezLopezyDoffoTP.ui.components.EmprendimientoItem
import com.undef.PerezLopezyDoffoTP.ui.components.MainScaffold
import com.undef.PerezLopezyDoffoTP.ui.components.SearchBar
import com.undef.PerezLopezyDoffoTP.ui.components.Spacer
import com.undef.PerezLopezyDoffoTP.ui.navigation.Screen
import com.undef.PerezLopezyDoffoTP.ui.viewModels.HomeViewModel



@Composable
fun HomeScreen(navController: NavController) {
    val homeViewModel: HomeViewModel = viewModel()
    val context = LocalContext.current
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        homeViewModel.setLocationPermission(granted)
    }
    LaunchedEffect(Unit) {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        homeViewModel.setLocationPermission(granted)
        if (!granted) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
    val locationGranted by homeViewModel.locationPermissionGranted.collectAsStateWithLifecycle()
    MainScaffold(navController = navController) { innerPadding ->
        Box(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
            .padding(16.dp)) {
            Home(
                modifier = Modifier.fillMaxWidth(),
                homeViewModel = homeViewModel,
                navController = navController,
                locationPermissionGranted = locationGranted,
                onRequestLocationPermission = {
                    locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            )
        }
    }
}

@Composable
fun Home(
    modifier: Modifier,
    homeViewModel: HomeViewModel,
    navController: NavController,
    locationPermissionGranted: Boolean,
    onRequestLocationPermission: () -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val emprendimientos by homeViewModel.emprendimientos.collectAsStateWithLifecycle()

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
            if (!locationPermissionGranted) {
                LocationPermissionReminder(onRequestPermission = onRequestLocationPermission)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        items(emprendimientos) { emprendimiento ->
            EmprendimientoItem(emprendimiento) { selectedEmprendimiento ->
                navController.navigate(Screen.EmprendimientoDetail.route.replace("{emprendimientoId}", selectedEmprendimiento.toString()))
            }
        }
    }
}

@Composable
private fun LocationPermissionReminder(onRequestPermission: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Activa tu ubicación",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Necesitamos permiso de ubicación para mostrarte los emprendimientos más cercanos.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = onRequestPermission) {
                Text(text = "Conceder permiso")
            }
        }
    }
}
