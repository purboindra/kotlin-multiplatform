package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.entity.data.Product
import org.example.project.navigation.LocalNavigationComponent
import org.example.project.navigation.NavigationComponent
import org.example.project.navigation.Router
import org.example.project.screen.Screen1
import org.example.project.screen.Screen2
import org.example.project.screen.Screen3
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    viewModel: AppViewModel = viewModel { AppViewModel() }
) {
    
    
    val stateModel by viewModel.stateModel.collectAsState()
    val navigationComponent = remember { NavigationComponent() }
    val router by navigationComponent.stateRouter.collectAsState()
    
    MaterialTheme {
        
        CompositionLocalProvider(
            LocalNavigationComponent provides navigationComponent
        ) {
            
            AnimatedContent(
                targetState = router
            ) {
                when (it) {
                    is Router.Screen1 -> {
                        Screen1()
                    }
                    
                    is Router.Screen2 -> {
                        Screen2()
                    }
                    
                    is Router.Screen3 -> {
                        Screen3()
                    }
                }
            }
        }
        
        
        LaunchedEffect(Unit) {
            viewModel.handleIntent(AppIntent.FetchApi)
            // viewModel.handleIntent(AppIntent.FetchProductById("1"))
        }


//        Column(
//            modifier = Modifier.fillMaxSize().padding(24.dp, vertical = 12.dp),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center,
//        ) {
//
//            Text("Counter: ${stateModel.counter}")
//            Spacer(modifier = Modifier.height(12.dp))
//            Button(onClick = {
//                viewModel.handleIntent(AppIntent.UpdateCounter)
//            }) {
//                Text("Increment")
//            }
//            Spacer(modifier = Modifier.height(12.dp))
//            with(stateModel.productResponseState) {
//                onIdle {
//                }
//                onSuccess { response ->
//                    SuccessContent(response)
//                }
//                onLoading {
//                    LoadingContent()
//                }
//                onFailure { throwable ->
//                    FailureContent(throwable)
//                }
//            }
//        }
    }
}

@Composable
fun LoadingContent() {
    CircularProgressIndicator()
}

@Composable
fun SuccessContent(product: Product) {
//    val data = product.orEmpty()
    Text(product.title)
//    LazyColumn(
//        modifier = Modifier.fillMaxSize(),
//        contentPadding = PaddingValues(12.dp)
//    ) {
//        itemsIndexed(data) { index, item ->
//            Surface(
//                modifier = Modifier.fillParentMaxWidth().padding(vertical = 6.dp),
//                color = MaterialTheme.colors.primary,
//                shape = RoundedCornerShape(12.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillParentMaxWidth()
//                        .padding(horizontal = 4.dp, vertical = 8.dp)
//                ) {
//                    Text(item.title)
//                }
//            }
//        }
//    }
}

@Composable
fun FailureContent(throwable: Throwable) {
    Text(
        throwable.message.orEmpty(),
        color = Color.Red
    )
}