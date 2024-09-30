package org.example.project.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import com.bumble.appyx.components.backstack.BackStack

interface Navigator {
    fun navigate(navTarget: NavTarget)
    fun back()
}

val LocalNavigator = staticCompositionLocalOf<Navigator> { error("navigator not implemented") }