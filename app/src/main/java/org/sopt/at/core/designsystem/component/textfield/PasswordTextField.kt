package org.sopt.at.core.designsystem.component.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.at.R
import org.sopt.at.core.designsystem.component.textfield.SoptValidator.isPasswordFormat

@Composable
fun PasswordTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = 15
) {
    var isFocused by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val iconId = if (isPasswordVisible) R.drawable.ic_visible_24 else R.drawable.ic_invisible_24

    val borderColor = remember(isFocused) {
        when {
            isFocused -> Color.White
            else -> Color.Unspecified
        }
    }

    BasicTextField(
        value = value,
        placeholder = placeholder,
        onValueChanged = { newText ->
            if (newText.length <= maxLength && isPasswordFormat(newText)) {
                onValueChanged(newText)
            }
        },
        onDoneAction = {
            keyboardController?.hide()
            focusManager.clearFocus()
        },
        borderColor = borderColor,
        modifier = modifier,
        onFocusChanged = {
            isFocused = it
        },
        isPasswordVisible = isPasswordVisible,
        trailingIcon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = iconId),
                contentDescription = null,
                modifier = Modifier.clickable(
                    onClick = { isPasswordVisible = !isPasswordVisible }
                ),
                tint = Color.Gray
            )
        }
    )
}

@Composable
@Preview
private fun PasswordTextFieldPreview() {
    var password by remember { mutableStateOf("") }
    PasswordTextField(
        value = password,
        onValueChanged = { password = it },
        placeholder = "비밀번호",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}
