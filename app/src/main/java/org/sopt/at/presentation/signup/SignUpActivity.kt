package org.sopt.at.presentation.signup

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import org.sopt.at.presentation.signup.id.SignUpIdRoute
import org.sopt.at.presentation.signup.password.SignUpPasswordRoute
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

class SignUpActivity : ComponentActivity() {
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
            ATSOPTANDROIDTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val context = LocalContext.current
                    var userId by remember { mutableStateOf("") }
                    var userPassword by remember { mutableStateOf("") }
                    var isNextStep by remember { mutableStateOf(true) }

                    if (isNextStep) {
                        SignUpIdRoute(
                            paddingValues = innerPadding,
                            userId = userId,
                            onUserIdChanged = { userId = it },
                            modifier = Modifier,
                            onBackClick = { (context as? Activity)?.onBackPressed() },
                            onNextClick = {
                                if (userId.length >= 6) {
                                    isNextStep = false
                                } else {
                                    Toast.makeText(context, "아이디는 최소 6자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    } else {
                        SignUpPasswordRoute(
                            paddingValues = innerPadding,
                            modifier = Modifier,
                            password = userPassword,
                            onPasswordChanged = { userPassword = it },
                            onBackClick = { isNextStep = true },
                            onNextClick = {
                                val isValidPassword = userPassword.length >= 8 &&
                                    userPassword.any { it.isDigit() } &&
                                    userPassword.any { it.isLetter() } &&
                                    userPassword.any { "!@#$%^&*~".contains(it) }

                                if (isValidPassword) {
                                    Toast.makeText(context, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                                    val resultIntent =
                                        Intent().apply {
                                            putExtra("USER_ID", userId)
                                            putExtra("USER_PASSWORD", userPassword)
                                        }
                                    setResult(RESULT_OK, resultIntent)
                                    finish()
                                } else {
                                    Toast.makeText(context, "비밀번호는 영문, 숫자, 특수문자를 포함한 8자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
