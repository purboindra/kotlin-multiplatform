package org.example.project

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.network.NetworkRepository
import org.example.project.network.State
import org.example.project.network.response.ProductResponse
import kotlin.coroutines.CoroutineContext

sealed class AppIntent {
    data object FetchApi : AppIntent()
}

class AppViewModel {
    
    private val networkRepository = NetworkRepository()
    
    private val mutableStateData: MutableStateFlow<State<ProductResponse>> = MutableStateFlow(
        State.Idle
    )
    
    val stateData: StateFlow<State<ProductResponse>> get() = mutableStateData
    
    private val viewModelScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext get() = SupervisorJob() + Dispatchers.Main
    }
    
    fun handleIntent(appIntent: AppIntent) {
        when (appIntent) {
            is AppIntent.FetchApi -> {
                fetchApi()
            }
        }
        
    }
    
    private fun fetchApi() = viewModelScope.launch {
        networkRepository.fetchProduct().stateIn(this).collect(mutableStateData)
    }
}