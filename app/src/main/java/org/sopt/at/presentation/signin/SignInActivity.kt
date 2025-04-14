package org.sopt.at.presentation.signin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import org.sopt.at.presentation.mypage.MyPageActivity
import org.sopt.at.presentation.signup.SignUpActivity
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

class SignInActivity : ComponentActivity() {
    private val signUpLauncher =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                validatedUserId = data?.getStringExtra("USER_ID")
                validatedUserPassword = data?.getStringExtra("USER_PASSWORD")
            }
        }

    private var validatedUserId: String? = null
    private var validatedUserPassword: String? = null

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

                    SignInRoute(
                        paddingValues = innerPadding,
                        onBackClick = { (context as? Activity)?.onBackPressed() },
                        userId = userId,
                        onUserIdChanged = { userId = it },
                        userPassword = userPassword,
                        onPasswordChanged = { userPassword = it },
                        onSignInClick = {
                            if (userId == validatedUserId &&
                                userPassword == validatedUserPassword
                            ) {
                                val intent =
                                    Intent(
                                        context,
                                        MyPageActivity::class.java
                                    ).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        putExtra("USER_ID", userId)
                                    }
                                context.startActivity(intent)
                            } else {
                                Toast.makeText(
                                    context,
                                    "아이디 또는 비밀번호가 일치하지 않습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onSignUpClick = {
                            val intent = Intent(context, SignUpActivity::class.java)
                            signUpLauncher.launch(intent)
                        }
                    )
                }
            }
        }
    }
}
