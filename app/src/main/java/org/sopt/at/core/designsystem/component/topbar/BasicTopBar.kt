package org.sopt.at.core.designsystem.component.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import org.sopt.at.R

@Composable
fun BasicTopBar(
    onBackClick:() -> Unit,
    modifier: Modifier = Modifier,
    trailingContent: @Composable (() -> Unit) = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black),
        horizontalArrangement = Arrangement.SpaceBetween,
        )
    {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_24),
            tint = Color.White,
            contentDescription = null,
            modifier = Modifier.clickable(onClick = onBackClick)
        )
        trailingContent()
    }
}

@Preview
@Composable
private fun BasicTopBarPreview() {
    BasicTopBar(onBackClick = {})
}
