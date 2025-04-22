package org.sopt.at.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import org.sopt.at.R
import org.sopt.at.core.designsystem.component.button.UrlImage

@Composable
fun HomeTopAppBar(
    imageUrl: String,
    onBroadCastClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_app_logo),
            contentDescription = null,
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_broadcast),
            contentDescription = "Broadcast",
            modifier = Modifier
                .clickable(onClick = onBroadCastClick)
                .size(24.dp),
            tint = Color.Unspecified
        )

        Spacer(modifier = Modifier.width(20.dp))

        UrlImage(
            url = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onProfileClick)
        )
    }
}
