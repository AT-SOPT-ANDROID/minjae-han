package org.sopt.at.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BasicButton(
    onClick: () -> Unit,
    buttonText: String,
    enabled: Boolean = false,
    modifier: Modifier = Modifier,
    textColor: Color = Color.LightGray,
    borderColor: Color = Color.Unspecified ,
    backgroundColor: Color = Color.DarkGray
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, color = borderColor, shape = RoundedCornerShape(4.dp))
            .clickable(onClick = onClick, enabled = enabled)
            .clip(RoundedCornerShape(8.dp))
            .background(backgroundColor)
            .padding(12.dp)
    ) {
        Text(
            text = buttonText,
            color = textColor
        )
    }
}

@Preview
@Composable
private fun BasicButtonPreview() {
    BasicButton(
        onClick = { },
        buttonText = "Click me",
        enabled = true
    )
}
