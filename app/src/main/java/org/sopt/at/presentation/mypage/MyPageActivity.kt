package org.sopt.at.presentation.mypage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

class MyPageActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            this.isAppearanceLightNavigationBars = false
            this.isAppearanceLightStatusBars = false
        }

        val userIdFromIntent = intent.getStringExtra("USER_ID") ?: ""

        setContent {
            ATSOPTANDROIDTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyPageRoute(
                        onBackClick = { },
                        onNotificationClick = { },
                        onSettingClick = { },
                        userId = userIdFromIntent,
                        paddingValues = innerPadding
                    )
                }
            }
        }
    }
}
