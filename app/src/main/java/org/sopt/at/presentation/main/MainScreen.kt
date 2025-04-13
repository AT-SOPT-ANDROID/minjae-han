import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.sopt.at.core.designsystem.component.snackbar.AtSoptSnackbar
import org.sopt.at.core.designsystem.event.LocalSnackBarTrigger
import org.sopt.at.presentation.main.MainNavigator
import org.sopt.at.presentation.main.MainTab
import org.sopt.at.presentation.main.component.MainBottomBar
import org.sopt.at.presentation.main.rememberMainNavigator
import org.sopt.at.presentation.mypage.navigation.myPageNavGraph
import org.sopt.at.presentation.signin.navigation.signInNavGraph
import org.sopt.at.presentation.signup.navigation.signUpIdNavGraph
import org.sopt.at.presentation.signup.navigation.signUpPasswordNavGraph

const val SHOW_SNACKBAR_TIMEMILLIS = 3000L

@Composable
fun MainScreen(
    navigator: MainNavigator = rememberMainNavigator()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val onShowSnackBar: (String) -> Unit = { message ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()
            val job = launch {
                snackBarHostState.showSnackbar(message)
            }
            delay(SHOW_SNACKBAR_TIMEMILLIS)
            job.cancel()
        }
    }

    AtSoptBackHandler(
        context = context,
        onShowSnackbar = {
            onShowSnackBar("한번 더 누르면 앱이 종료돼요!")
        }
    )

    CompositionLocalProvider(
        LocalSnackBarTrigger provides onShowSnackBar
    ) {
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                    AtSoptSnackbar(text = snackbarData.visuals.message)
                }
            },
            bottomBar = {
                MainBottomBar(
                    visible = navigator.shouldShowBottomBar(),
                    tabs = MainTab.entries.toPersistentList(),
                    currentTab = navigator.currentTab,
                    onTabSelected = navigator::navigate
                )
            }
        ) { paddingValues ->
            NavHost(
                navController = navigator.navController,
                startDestination = navigator.startDestination
            ) {
                signInNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = { },
                    navigateToHome = { },
                    navigateToSignUp = { }
                )
                signUpIdNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = { },
                    navigateToSignUpPassword = { },
                )
                signUpPasswordNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = { },
                    navigateToSignIn = { navigator.navigateToSignIn() }
                )
                myPageNavGraph(
                    paddingValues = paddingValues,
                    navigateUp = { },
                    navigateToNotification = { },
                    navigateToSetting = { },
                    navigateToSignIn = { navigator.navigateToSignIn() }
                )
            }
        }
    }
}

@Composable
fun AtSoptBackHandler(
    context: Context,
    enabled: Boolean = true,
    exitDelayMillis: Long = 3000L,
    onShowSnackbar: () -> Unit = {}
) {
    var backPressedTime by remember {
        mutableLongStateOf(0L)
    }

    BackHandler(enabled = enabled) {
        if (System.currentTimeMillis() - backPressedTime <= exitDelayMillis) {
            (context as Activity).finish()
        } else {
            onShowSnackbar()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
