package org.sopt.at.presentation

import MainScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.at.core.designsystem.theme.ATSOPTANDROIDTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Black.hashCode()),
            navigationBarStyle = SystemBarStyle.dark(Color.Black.hashCode())
        )
        setContent {
            ATSOPTANDROIDTheme {
                MainScreen()
            }
        }
    }
}
