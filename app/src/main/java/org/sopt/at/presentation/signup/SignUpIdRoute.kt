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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.sopt.at.core.designsystem.component.button.BasicButton
import org.sopt.at.core.designsystem.component.textfield.UserIdTextField
import org.sopt.at.core.designsystem.component.topbar.BasicTopBar
import org.sopt.at.core.util.extension.addFocusCleaner
import org.sopt.at.core.util.extension.imePadding

@Composable
fun SignUpIdRoute(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    onNextClick: () -> Unit
) {
    var userId by remember { mutableStateOf("") }

    SignUpIdScreen(
        onBackClick = onBackClick,
        userId = userId,
        onUserIdChanged = { userId = it },
        onNextClick = onNextClick,
        paddingValues = paddingValues
    )
}

@Composable
fun SignUpIdScreen(
    paddingValues: PaddingValues,
    onBackClick: () -> Unit,
    userId: String,
    onUserIdChanged: (String) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
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
            onValueChanged = onUserIdChanged,
            onDoneAction = {
                keyboardController?.hide()
                focusManager.clearFocus()
            },
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
