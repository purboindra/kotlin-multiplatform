package org.example.project.navigation

import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.example.project.screen.Screen1
import kotlin.coroutines.CoroutineContext

sealed class Router {
    data object Screen1 : Router()
    data object Screen2 : Router()
    data object Screen3 : Router()
}

class NavigationComponent {
    
    private val backStack: MutableList<Router> = mutableListOf(Router.Screen1)
    
    private val mutableStateRouter: MutableStateFlow<Router> = MutableStateFlow(Router.Screen1)
    val stateRouter: StateFlow<Router> get() = mutableStateRouter
    
    private val coroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext get() = SupervisorJob() + Dispatchers.Main
    }
    
    fun navigate(router: Router) {
        mutableStateRouter.value = router
        if (!backStack.map { it::class.simpleName }.contains(router::class.simpleName)) {
            backStack.add(router)
        }
    }
    
    fun back() = coroutineScope.launch {
        backStack.removeLast().also {
            println("backstack remove -> $it")
        }
        
        println("backstack -> $backStack")
        val currentRouter = backStack.lastOrNull() ?: Router.Screen1
        navigate(router = currentRouter)
    }
}

val LocalNavigationComponent = staticCompositionLocalOf<NavigationComponent> {
    error("Navigation not provided")
}