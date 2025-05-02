package org.sopt.at.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.collections.immutable.ImmutableList
import org.sopt.at.presentation.home.component.CategorySection
import org.sopt.at.presentation.home.component.ContentList
import org.sopt.at.presentation.home.component.HomeImageBanner
import org.sopt.at.presentation.home.component.HomePagerBanner
import org.sopt.at.presentation.home.component.HomeTopAppBar

@Composable
fun HomeRoute(
    paddingValues: PaddingValues,
    navigateToMyPage: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomeScreen(
        bannerImages = viewModel.bannerImages,
        topContent = viewModel.topContent,
        onBroadCastClick = {},
        onProfileClick = navigateToMyPage,
        modifier = modifier.padding(paddingValues)
    )
}

@Composable
private fun HomeScreen(
    bannerImages: ImmutableList<Int>,
    topContent: ImmutableList<ContentList>,
    onBroadCastClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeTopAppBar(
            imageUrl = "https://avatars.githubusercontent.com/u/160750136?v=4",
            onBroadCastClick = onBroadCastClick,
            onProfileClick = onProfileClick,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        CategorySection(modifier = Modifier.padding(vertical = 12.dp))

        HomePagerBanner(images = bannerImages)

        SubTitleText(text = "오늘의 티빙 TOP 5")

        HomeImageBanner(contentList = topContent)

        SubTitleText(text = "지금 방영 중인 콘텐츠")

        HomeImageBanner(contentList = topContent, showRank = false)
    }
}

@Composable
private fun SubTitleText(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}
