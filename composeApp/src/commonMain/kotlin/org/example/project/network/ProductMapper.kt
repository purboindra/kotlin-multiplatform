package org.example.project.network

import org.example.project.network.data.Product
import org.example.project.network.response.ProductResponse

object ProductMapper {
    
    fun mapResponseToProduct(productResponse: ProductResponse?): Product {
        val title = productResponse?.firstOrNull()?.title
        val description = productResponse?.firstOrNull()?.description
        
        return Product(
            title = title.orEmpty()
        )
    }
    
}