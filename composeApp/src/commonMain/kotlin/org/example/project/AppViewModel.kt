package org.example.project

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.base.BaseViewModel
import org.example.project.network.ProductRepository
import org.example.project.network.State
import org.example.project.network.data.Product
import org.example.project.network.response.ProductResponse
import org.example.project.network.response.ProductResponseItem

sealed class AppIntent {
    data object FetchApi : AppIntent()
    data class FetchProductById(val id: String) : AppIntent()
    data object UpdateCounter : AppIntent()
}

data class AppModel(
    val counter: Int = 0,
    val productResponseState: State<Product> = State.Idle,
    val productByIdResponseState: State<ProductResponseItem> = State.Idle,
)

class AppViewModel(
    private val productRepository: ProductRepository = ProductRepository()
) : BaseViewModel<AppModel, AppIntent>(AppModel()) {


//    private val viewModelScope = object : CoroutineScope {
//        override val coroutineContext: CoroutineContext get() = SupervisorJob() + Dispatchers.Main
//    }
    
    
    override fun handleIntent(appIntent: AppIntent) {
        when (appIntent) {
            is AppIntent.FetchApi -> fetchApi()
            is AppIntent.FetchProductById -> fetchProductById(appIntent.id)
            is AppIntent.UpdateCounter -> updateCounter()
        }
    }
    
    private fun updateCounter() {
        updateModel { it.copy(counter = it.counter + 1) }
    }
    
    private fun fetchProductById(id: String) = viewModelScope.launch {
        productRepository.fetchProductById(id).stateIn(this).collectLatest { state ->
            updateModel {
                it.copy(
//                    productByIdResponseState = state
                )
            }
        }
    }
    
    private fun fetchApi() = viewModelScope.launch {
        productRepository.fetchProduct().stateIn(this).collectLatest { state ->
            updateModel {
                it.copy(
                    productResponseState = state
                )
            }
        }
    }
    
}