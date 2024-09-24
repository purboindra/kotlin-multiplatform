package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinmultiplatformproject.composeapp.generated.resources.Res
import kotlinmultiplatformproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import org.example.project.network.NetworkRepository
import org.example.project.network.State
import org.example.project.network.response.ProductResponse
import org.example.project.network.response.ProductResponseItem

@Composable
@Preview
fun App() {
    MaterialTheme {
        
        val viewModel = remember { AppViewModel() }
        val productResponseState by viewModel.stateData.collectAsState()
        
        LaunchedEffect(Unit) {
            viewModel.handleIntent(AppIntent.FetchApi)
        }
        
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            with(productResponseState) {
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
fun SuccessContent(productResponse: ProductResponse) {
    val data = productResponse.orEmpty()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp)
    ) {
        itemsIndexed(data) { index, item ->
            Text("Item $index: ${item.title}")
        }
    }
}

@Composable
fun FailureContent(throwable: Throwable) {
    Text(
        throwable.message.orEmpty(),
        color = Color.Red
    )
}