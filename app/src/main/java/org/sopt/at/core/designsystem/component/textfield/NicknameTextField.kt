package org.sopt.at.core.designsystem.component.textfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.sopt.at.core.util.validation.SoptValidator.isNicknameFormat

@Composable
fun NicknameTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    onDoneAction: () -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    maxLength: Int = 20
) {
    var isFocused by remember { mutableStateOf(false) }

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
            if (newText.length <= maxLength && isNicknameFormat(newText)) {
                onValueChanged(newText)
            } else {
                onValueChanged(value)
            }
        },
        onDoneAction = onDoneAction,
        borderColor = borderColor,
        modifier = modifier,
        onFocusChanged = {
            isFocused = it
        }
    )
}
