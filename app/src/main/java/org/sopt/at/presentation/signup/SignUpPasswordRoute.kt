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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.PasswordTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar

@Composable
fun SignUpPasswordRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var password by remember { mutableStateOf("") }

    SignUpPasswordScreen(
        onBackClick = onBackClick,
        password = password,
        onPasswordChanged = { password = it },
        onNextClick = onNextClick,
        paddingValues = paddingValues
    )
}

@Composable
fun SignUpPasswordScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    password: String,
    onPasswordChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier.Companion
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Companion.Black)
            .padding(paddingValues)
    ) {
        BasicTopBar(onBackClick = onBackClick)

        Spacer(modifier = Modifier.Companion.height(40.dp))

        Text(
            text = "비밀번호를 입력해주세요.",
            fontSize = 28.sp,
            color = Color.Companion.White,
            fontWeight = FontWeight.Companion.Bold,
            textAlign = TextAlign.Companion.Center,
            modifier = Modifier.Companion.fillMaxWidth()
        )
        Spacer(modifier = Modifier.Companion.height(20.dp))

        PasswordTextField(
            value = password,
            onValueChanged = onPasswordChanged,
            placeholder = "비밀번호",
            modifier = Modifier.Companion.fillMaxWidth()
        )

        Spacer(modifier = Modifier.Companion.height(16.dp))

        Text(
            text = "영문, 숫자, 특수문자(~!@#$%^&*) 조합 8 ~ 15자리",
            fontSize = 12.sp,
            color = Color.Companion.Gray,
            textAlign = TextAlign.Companion.Start,
            modifier = Modifier.Companion.fillMaxWidth()
        )

        Spacer(modifier = Modifier.Companion.weight(1f))

        BasicButton(
            onClick = onNextClick,
            enabled = true,
            buttonText = "다음",
            borderColor = Color.Companion.LightGray,
            textColor = Color.Companion.White,
            backgroundColor = Color.Companion.Black
        )
    }
}
