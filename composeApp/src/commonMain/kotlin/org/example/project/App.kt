package org.example.project

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocal
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.entity.data.Product
import org.example.project.navigation.LocalNavigationController
import org.example.project.navigation.Routers
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
    
    MaterialTheme {
        
        val navigationController = rememberNavController()
        
        CompositionLocalProvider(LocalNavigationController provides navigationController) {
            NavHost(navController = navigationController, startDestination = Routers.SCREEN_1) {
                composable(
                    route = Routers.SCREEN_1
                ) {
                    Screen1()
                }
                
                composable(
                    route = Routers.SCREEN_2,
                    arguments = listOf(
                        navArgument(name = "name") {
                            this.defaultValue = ""
                        }
                    )
                ) {
                    val name = it.arguments?.getString("name").orEmpty()
                    
                    Screen2(name)
                }
                
                composable(
                    route = Routers.SCREEN_3
                ) {
                    Screen3()
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