package org.example.project.repository

import kotlinx.coroutines.flow.Flow
import org.example.project.base.BaseRepository
import org.example.project.entity.ProductMapper
import org.example.project.base.State
import org.example.project.entity.data.Product
import org.example.project.entity.response.ProductResponse
import org.example.project.entity.response.ProductResponseItem

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
