package org.example.project.network

import kotlinx.coroutines.flow.Flow
import org.example.project.base.BaseRepository
import org.example.project.network.data.Product
import org.example.project.network.response.ProductResponse
import org.example.project.network.response.ProductResponseItem

class ProductRepository : BaseRepository() {
    
    fun fetchProductById(id: String): Flow<State<Product>> {
        return suspend {
            fetchHttpResponse("https://fakestoreapi.com/products/$id")
        }.reduce<ProductResponseItem, Product> {
//            val product=ProductMapper.mapResponseToProduct(it)
            val product = ProductMapper.mapResponseToProduct(null)
            State.Success(product)
        }
    }
    
    fun fetchProduct(): Flow<State<Product>> {
        return suspend {
            fetchHttpResponse("https://fakestoreapi.com/products")
        }.reduce<ProductResponse, Product> {
            val product = ProductMapper.mapResponseToProduct(it)
            State.Success(product)
        }
    }
}
