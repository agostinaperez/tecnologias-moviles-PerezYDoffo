package com.undef.PerezLopezyDoffoTP

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.undef.PerezLopezyDoffoTP.ui.navigation.SetupNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
            ) {
                SetupNavigation()
            }
        }
    }
}