package org.sopt.at.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.core.state.UiState
import org.sopt.at.presentation.mypage.component.MyPageTopAppBar

@Composable
fun MyPageRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onSettingClick: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle()
    val nickname by viewModel.nickname.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    val snackbarTrigger = LocalSnackBarTrigger.current
    
    LaunchedEffect(viewModel.sideEffect, lifecycleOwner) {
        viewModel.sideEffect.flowWithLifecycle(lifecycleOwner.lifecycle).collect { effect ->
            when (effect) {
                is MyPageSideEffect.ShowSnackbar -> {
                    snackbarTrigger(effect.message)
                }
            }
        }
    }

    MyPageScreen(
        onBackClick = onBackClick,
        userId = userId ?: "",
        nickname = nickname ?: "로딩 중...",
        isLoading = uiState is UiState.Loading,
        onNotificationClick = onNotificationClick,
        onSettingClick = onSettingClick,
        onSignOutClick = {
            viewModel.signOut()
            onSignOutClick()
        },
        paddingValues = paddingValues
    )
}

@Composable
fun MyPageScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    userId: String,
    nickname: String,
    isLoading: Boolean,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    onSignOutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(paddingValues)
            .padding(20.dp),
        verticalArrangement = Arrangement.Top
    ) {
        MyPageTopAppBar(
            onBackClick = onBackClick,
            onNotificationClick = onNotificationClick,
            onSettingClick = onSettingClick,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "환영합니다. $userId 님",
            color = Color.White,
            fontSize = 20.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = if (isLoading) "닉네임 불러오는 중..." else "닉네임: $nickname",
            color = Color.White,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onSignOutClick,
            enabled = true,
            buttonText = "로그아웃",
            borderColor = Color.DarkGray,
            textColor = Color.Gray,
            backgroundColor = Color.Black
        )
    }
}
