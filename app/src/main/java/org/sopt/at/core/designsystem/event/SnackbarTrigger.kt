package org.sopt.at.core.designsystem.event

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarTrigger = staticCompositionLocalOf<(String) -> Unit> {
    error("No SnackBar provided")
}
