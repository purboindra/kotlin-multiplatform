package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.example.project.network.response.ProductResponse
import org.example.project.network.response.ProductResponseItem

class NetworkRepository {
    
    private val client = HttpClient {
        install(ContentNegotiation) {
            json()
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }
    
    private suspend fun fetchDataProductById(id: String): HttpResponse {
        val response = client.get("https://fakestoreapi.com/products/$id")
        return response
    }
    
    fun fetchProductById(id: String): Flow<State<ProductResponseItem>> {
        return suspend {
            fetchDataProductById(id)
        }.reduce<ProductResponseItem> {
            State.Success(it)
        }
    }
    
    private suspend fun fetchDataResponse(): HttpResponse {
        val response = client.get("https://fakestoreapi.com/products")
        println("statusCode: ${response.status}")
        return response
    }
    
    fun fetchProduct(): Flow<State<ProductResponse>> {
        return suspend {
            fetchDataResponse()
        }.reduce<ProductResponse> {
            State.Success(it)
        }
    }
}

inline fun <reified T> (suspend () -> HttpResponse).reduce(
    crossinline block: (T) -> State<T>
): Flow<State<T>> {
    return flow {
        val httpResponse = invoke()
        if (httpResponse.status.isSuccess()) {
            val data = httpResponse.body<T>()
            emit(block.invoke(data))
        } else {
            val throwable = Throwable(httpResponse.bodyAsText())
            val state = State.Failure(throwable)
            emit(
                state
            )
        }
    }.onStart {
        emit(State.Loading)
    }.catch {
        val state = State.Failure(it)
        emit(state)
    }
}