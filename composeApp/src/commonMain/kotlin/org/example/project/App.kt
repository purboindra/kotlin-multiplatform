package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.network.data.Product
import org.example.project.network.response.ProductResponse
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    viewModel: AppViewModel = viewModel { AppViewModel() }
) {
    
    
    val stateModel by viewModel.stateModel.collectAsState()
    
    MaterialTheme {
        
        
        LaunchedEffect(Unit) {
            viewModel.handleIntent(AppIntent.FetchApi)
            // viewModel.handleIntent(AppIntent.FetchProductById("1"))
        }
        
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            
            Text("Counter: ${stateModel.counter}")
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                viewModel.handleIntent(AppIntent.UpdateCounter)
            }) {
                Text("Increment")
            }
            Spacer(modifier = Modifier.height(12.dp))
            with(stateModel.productResponseState) {
                onIdle {
                }
                onSuccess { response ->
                    SuccessContent(response)
                }
                onLoading {
                    LoadingContent()
                }
                onFailure { throwable ->
                    FailureContent(throwable)
                }
            }
        }
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