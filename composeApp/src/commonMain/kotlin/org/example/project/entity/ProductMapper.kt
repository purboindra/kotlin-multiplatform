package org.example.project.entity

import org.example.project.entity.data.Product
import org.example.project.entity.response.ProductResponse

object ProductMapper {
    
    fun mapResponseToProduct(productResponse: ProductResponse?): Product {
        val title = productResponse?.firstOrNull()?.title
        val description = productResponse?.firstOrNull()?.description
        
        return Product(
            title = title.orEmpty()
        )
    }
    
}