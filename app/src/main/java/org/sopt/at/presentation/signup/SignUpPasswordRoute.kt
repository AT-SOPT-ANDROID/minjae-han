package org.sopt.at.presentation.signup

import androidx.compose.foundation.background
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.PasswordTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.core.state.UiState
import org.sopt.at.core.util.extension.addFocusCleaner
import org.sopt.at.core.util.extension.imePadding

@Composable
fun SignUpPasswordRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: SignUpViewModel
) {
    val state by viewModel.state.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarTrigger = LocalSnackBarTrigger.current

    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is SignUpSideEffect.ShowSnackbar -> {
                    snackbarTrigger(effect.message)
                }
            }
        }
    }

    LaunchedEffect(uiState) {
        if (uiState is UiState.Success) {
            onNextClick()
        }
    }

    val isButtonEnabled = viewModel.isValidPassword(state.password)

    SignUpPasswordScreen(
        onBackClick = onBackClick,
        password = state.password,
        onPasswordChanged = viewModel::updatePassword,
        onNextClick = viewModel::saveUserCredentials,
        isButtonEnabled = isButtonEnabled,
        paddingValues = paddingValues
    )
}

@Composable
private fun SignUpPasswordScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .addFocusCleaner(focusManager)
            .padding(paddingValues)
            .padding(20.dp)
            .imePadding()
    ) {
        BasicTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "비밀번호를 입력해주세요.",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        PasswordTextField(
            value = password,
            onValueChanged = onPasswordChanged,
            placeholder = "비밀번호",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "영문, 숫자, 특수문자(~!@#$%^&*) 조합 8 ~ 15자리",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onNextClick,
            enabled = isButtonEnabled,
            buttonText = "다음",
            borderColor = if (isButtonEnabled) Color.White else Color.LightGray,
            textColor = Color.White,
            backgroundColor = if (isButtonEnabled) Color.Gray else Color.Black
        )
    }
}
