package org.sopt.at.presentation.home.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.collections.immutable.ImmutableList

data class ContentList(
    @DrawableRes val imageRes: Int,
    val isNew: Boolean = false
)

@Composable
fun HomeImageBanner(
    contentList: ImmutableList<ContentList>,
    modifier: Modifier = Modifier,
    showRank: Boolean = true
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(contentList) { index, content ->
            BannerItem(
                content = content,
                position = index + 1,
                showRank = showRank
            )
        }
    }
}

@Composable
private fun BannerItem(
    content: ContentList,
    position: Int,
    showRank: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.Bottom
    ) {
        if (showRank) {
            Text(
                text = "$position",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                fontSize = 70.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
        Box(
            modifier = modifier
                .width(120.dp)
                .aspectRatio(1f / 1.6f)
                .clip(RoundedCornerShape(8.dp))
        ) {
            Image(
                painter = painterResource(id = content.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            if (content.isNew) {
                NewCard(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                )
            }
        }
    }
}

@Composable
private fun NewCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = Color.Red
    ) {
        Text(
            text = "NEW",
            color = Color.White,
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}
