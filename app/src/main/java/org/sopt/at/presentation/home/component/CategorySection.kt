package org.sopt.at.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun CategorySection(
    modifier: Modifier = Modifier,
    categories: ImmutableList<String> = persistentListOf(
        "DRAMA",
        "VARIETY",
        "MOVIE",
        "SPORTS",
        "ANIMATION",
        "NEWS",
        "KIDS"
    )
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Black)
    ) {
        items(categories.size) { index ->
            CategoryItem(
                text = categories[index]
            )
        }
    }
}

@Composable
private fun CategoryItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = modifier
            .padding(vertical = 8.dp)
    )
}
