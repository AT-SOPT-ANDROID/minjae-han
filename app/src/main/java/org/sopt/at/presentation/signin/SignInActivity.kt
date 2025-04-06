package org.sopt.at.presentation.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.sopt.at.presentation.signup.SignUpActivity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

class SignInActivity : ComponentActivity() {
    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            this.isAppearanceLightNavigationBars = false
            this.isAppearanceLightStatusBars = false
        }
        setContent {
            ATSOPTANDROIDTheme(
                darkTheme = true
            ) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    SignInRoute(
                        paddingValues = innerPadding,
                        onBackClick = { (context as? Activity)?.onBackPressed() },
                        onSignUpClick = {
                            val intent = android.content.Intent(
                                context,
                                SignUpActivity::class.java
                            )
                            context.startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}
