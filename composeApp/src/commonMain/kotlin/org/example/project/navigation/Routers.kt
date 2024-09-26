package org.example.project.navigation

import androidx.navigation.NavHostController
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.serialization.Serializable

@Serializable
object Screen1

@Serializable
data class Screen2(val name: String? = null)

object Routers {
    const val SCREEN_1 = "SCREEN_1"
    const val SCREEN_2 = "SCREEN_2/{name}"
    const val SCREEN_3 = "SCREEN_3"
}

val LocalNavigationController =
    staticCompositionLocalOf<NavHostController> { error("Nav host not implemented") }