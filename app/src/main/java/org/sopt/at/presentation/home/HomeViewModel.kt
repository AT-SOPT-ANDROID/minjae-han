package org.sopt.at.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.sopt.at.R
import org.sopt.at.presentation.home.component.ContentList

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    val bannerImages: ImmutableList<Int> = persistentListOf(
        R.drawable.img_home_main_banner1,
        R.drawable.img_home_main_banner2,
        R.drawable.img_home_main_banner3,
        R.drawable.img_home_main_banner4,
        R.drawable.img_home_main_banner5
    )

    val topContent: ImmutableList<ContentList> = persistentListOf(
        ContentList(imageRes = bannerImages[0], isNew = true),
        ContentList(imageRes = bannerImages[1]),
        ContentList(imageRes = bannerImages[2], isNew = true),
        ContentList(imageRes = bannerImages[3]),
        ContentList(imageRes = bannerImages[4])
    )
}
