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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.NicknameTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.core.util.extension.addFocusCleaner
import org.sopt.at.core.util.extension.imePadding

@Composable
fun SignUpNicknameRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    viewModel: SignUpViewModel
) {
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

    val state by viewModel.state.collectAsStateWithLifecycle()
    val isButtonEnabled = viewModel.isValidNickname(state.nickname)

    SignUpNicknameScreen(
        onBackClick = onBackClick,
        userNickName = state.nickname,
        onUserIdChanged = viewModel::updateNickname,
        onNextClick = onNextClick,
        isButtonEnabled = isButtonEnabled,
        paddingValues = paddingValues
    )
}

@Composable
private fun SignUpNicknameScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    userNickName: String,
    onUserIdChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    isButtonEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val buttonColors = remember {
        derivedStateOf {
            Triple(
                if (isButtonEnabled) Color.White else Color.LightGray,
                Color.White,
                if (isButtonEnabled) Color.Gray else Color.Black
            )
        }
    }

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
            text = "닉네임을 입력해주세요.",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        NicknameTextField(
            value = userNickName,
            onValueChanged = onUserIdChanged,
            onDoneAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
            placeholder = "닉네임",
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "닉네임은 한글/영어/숫자만 사용 가능하며 1자 ~ 20자 이어야 합니다.",
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
            borderColor = buttonColors.value.first,
            textColor = buttonColors.value.second,
            backgroundColor = buttonColors.value.third
        )
    }
}
