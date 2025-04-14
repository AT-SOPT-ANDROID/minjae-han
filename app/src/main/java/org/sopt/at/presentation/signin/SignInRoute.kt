package org.sopt.at.presentation.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.PasswordTextField
import org.sopt.at.core.designsystem.component.textfield.UserIdTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar
import org.sopt.at.presentation.signin.component.MenuList
import org.sopt.at.ui.theme.ATSOPTANDROIDTheme

@Composable
fun SignInRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    userPassword: String,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SignInScreen(
        onBackClick = onBackClick,
        userId = userId,
        onUserIdChanged = onUserIdChanged,
        userPassword = userPassword,
        onPasswordChanged = onPasswordChanged,
        onSignInClick = onSignInClick,
        onSignUpClick = onSignUpClick,
        modifier = modifier.padding(paddingValues)
    )
}

@Composable
fun SignInScreen(
    onBackClick: () -> Unit,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    userPassword: String,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        BasicTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "TIVING ID 로그인",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        UserIdTextField(
            value = userId,
            placeholder = "아이디",
            onValueChanged = onUserIdChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            value = userPassword,
            placeholder = "비밀번호",
            onValueChanged = onPasswordChanged
        )

        Spacer(modifier = Modifier.height(20.dp))

        BasicButton(
            onClick = onSignInClick,
            enabled = true,
            buttonText = "로그인 하기",
            borderColor = Color.DarkGray,
            textColor = Color.Gray
        )

        Spacer(modifier = Modifier.height(20.dp))

        MenuList(onSignUpClick = onSignUpClick)

        Spacer(modifier = Modifier.height(20.dp))

        val annotatedText = buildAnnotatedString {
            append("이 사이트는 Google reCAPTCHA로 보호되며,\n")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("Google 개인정보 처리방침")
            }
            append("과 ")
            withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                append("서비스 약관")
            }
            append("이 적용됩니다.\n")
        }

        Text(
            text = annotatedText,
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    ATSOPTANDROIDTheme {
        SignInScreen(
            onBackClick = { },
            userId = "",
            onUserIdChanged = { },
            userPassword = "",
            onPasswordChanged = { },
            onSignInClick = { },
            onSignUpClick = { }
        )
    }
}
