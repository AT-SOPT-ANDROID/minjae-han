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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.presentation.mypage.component.MyPageTopAppBar

@Composable
fun MyPageRoute(
    userId: String,
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    MyPageScreen(
        onBackClick = onBackClick,
        userId = userId,
        onNotificationClick = onNotificationClick,
        onSettingClick = onSettingClick,
        modifier = modifier.padding(paddingValues)
    )
}

@Composable
fun MyPageScreen(
    onBackClick: () -> Unit,
    userId: String,
    onNotificationClick: () -> Unit,
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .background(Color.Black)
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

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = {},
            enabled = true,
            buttonText = "로그아웃",
            borderColor = Color.DarkGray,
            textColor = Color.Gray,
            backgroundColor = Color.Black
        )
    }
}
