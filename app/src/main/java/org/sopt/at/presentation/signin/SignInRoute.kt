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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.PasswordTextField
import org.sopt.at.core.designsystem.component.textfield.UserIdTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.core.state.UiState
import org.sopt.at.core.util.extension.addFocusCleaner
import org.sopt.at.presentation.signin.component.MenuList

@Composable
fun SignInRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onSignInClick: () -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarTrigger = LocalSnackBarTrigger.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is SignInSideEffect.ShowSnackbar -> {
                    snackbarTrigger(effect.message)
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onSignInClick()
        }
    }

    SignInScreen(
        onBackClick = onBackClick,
        userId = state.userId,
        onUserIdChanged = viewModel::updateUserId,
        userPassword = state.password,
        onPasswordChanged = viewModel::updatePassword,
        onSignInClick = viewModel::signIn,
        onSignUpClick = onSignUpClick,
        isButtonEnabled = state.isInputValid,
        paddingValues = paddingValues
    )
}

@Composable
fun SignInScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    userPassword: String,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val buttonColors = remember {
        derivedStateOf {
            Triple(
                if (isButtonEnabled) Color.White else Color.DarkGray,
                if (isButtonEnabled) Color.Black else Color.White,
                if (isButtonEnabled) Color.White else Color.DarkGray
            )
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .addFocusCleaner(focusManager)
            .background(Color.Black)
            .padding(paddingValues)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        BasicTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "TVING ID 로그인",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))

        UserIdTextField(
            value = userId,
            placeholder = "아이디",
            onValueChanged = onUserIdChanged,
            onDoneAction = {
                focusManager.moveFocus(FocusDirection.Next)
            }
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
            enabled = isButtonEnabled,
            buttonText = "로그인 하기",
            borderColor = buttonColors.value.first,
            textColor = buttonColors.value.second,
            backgroundColor = buttonColors.value.third
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
