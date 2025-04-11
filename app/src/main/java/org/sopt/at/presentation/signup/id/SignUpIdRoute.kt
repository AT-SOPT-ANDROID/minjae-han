package org.sopt.at.presentation.signup.id

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.UserIdTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar

@Composable
fun SignUpIdRoute(
    paddingValues: PaddingValues,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SignUpIdScreen(
        onBackClick = onBackClick,
        userId = userId,
        onUserIdChanged = onUserIdChanged,
        onNextClick = onNextClick,
        modifier = modifier.padding(paddingValues)
    )
}

@Composable
fun SignUpIdScreen(
    onBackClick: () -> Unit,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(20.dp)
    ) {
        BasicTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "아이디를 입력해주세요.",
            fontSize = 28.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))

        UserIdTextField(
            value = userId,
            placeholder = "아이디",
            onValueChanged = onUserIdChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "영문 소문자 또는 영문 소문자, 숫자 조합 6 ~ 12자리",
            fontSize = 12.sp,
            color = Color.Gray,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        BasicButton(
            onClick = onNextClick,
            enabled = true,
            buttonText = "다음",
            borderColor = Color.LightGray,
            textColor = Color.White,
            backgroundColor = Color.Black
        )
    }
}

@Preview
@Composable
private fun SignUpIdScreenPreview() {
    SignUpIdScreen(
        onBackClick = { },
        userId = "",
        onUserIdChanged = { },
        onNextClick = { }
    )
}
