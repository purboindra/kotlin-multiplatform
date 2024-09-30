package org.example.project.navigation

import androidx.compose.animation.core.spring
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.fader.BackStackFader
import com.bumble.appyx.components.backstack.ui.parallax.BackStackParallax
import com.bumble.appyx.interactions.gesture.GestureFactory
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node
import org.example.project.isAndroid
import org.example.project.screen.Screen1
import org.example.project.screen.Screen2
import org.example.project.screen.Screen3

class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<NavTarget> = BackStack(
        model = BackStackModel(
            initialTarget = NavTarget.Screen1,
            savedStateMap = nodeContext.savedStateMap
        ),
        visualisation = {
            if (isAndroid) {
                BackStackFader(it, defaultAnimationSpec = spring())
            } else {
                BackStackParallax(it)
            }
        },
        gestureFactory = {
            if (isAndroid) {
                GestureFactory.Noop()
            } else {
                BackStackParallax.Gestures(it)
            }
        }
    )
) : Node<NavTarget>(nodeContext = nodeContext, appyxComponent = backStack) {
    override fun buildChildNode(navTarget: NavTarget, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            is NavTarget.Screen1 -> node(nodeContext = nodeContext) {
                Screen1()
            }
            
            is NavTarget.Screen2 -> node(nodeContext = nodeContext) {
                org.example.project.screen.Screen2(navTarget.name)
            }
            
            is NavTarget.Screen3 -> node(nodeContext = nodeContext) {
                Screen3()
            }
        }
    }
}