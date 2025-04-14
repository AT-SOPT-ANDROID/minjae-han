package org.sopt.at.core.designsystem.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.sopt.at.core.designsystem.component.textfield.SoptValidator.isUserIdFormat

@Composable
fun UserIdTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = 12
) {
    var isFocused by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

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
            if (newText.length <= maxLength && isUserIdFormat(newText)) {
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
        }
    )
}

@Preview
@Composable
private fun UserIdTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    UserIdTextField(
        value = text,
        onValueChanged = { text = it },
        placeholder = "아이디",
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}
