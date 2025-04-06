package org.sopt.at.core.designsystem.component.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BasicTextField(
    value: String,
    placeholder: String,
    onValueChanged: (String) -> Unit,
    borderColor: Color,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.DarkGray,
    imeAction: ImeAction = ImeAction.Done,
    onDoneAction: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester(),
    isPasswordVisible: Boolean = true,
    singleLine: Boolean = true,
    leadingIcon: @Composable () -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
) {
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            onValueChanged(newValue)
        },
        visualTransformation =
            if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation()
            },
        modifier =
            modifier
                .clip(RoundedCornerShape(8.dp))
                .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                .background(backgroundColor)
                .padding(horizontal = 12.dp)
                .focusRequester(focusRequester)
                .onFocusChanged { focusState -> onFocusChanged(focusState.isFocused) },
        singleLine = singleLine,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions =
            KeyboardActions(
                onDone = { onDoneAction() },
            ),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                leadingIcon()
                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(vertical = 12.dp),
                ) {
                    innerTextField()
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = Color.Gray,
                            fontSize = 16.sp,
                        )
                    }
                }
                trailingIcon()
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun BasicTextFieldPreview() {
    var text by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        placeholder = "플레이스 홀더",
        onValueChanged = { newText ->
            text = newText
        },
        borderColor = Color.Unspecified,
        onFocusChanged = { focused ->
            isFocused = focused
        },
    )
}
