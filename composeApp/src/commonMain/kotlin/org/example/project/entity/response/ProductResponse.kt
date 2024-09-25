package org.example.project.entity.response


import kotlinx.serialization.Serializable

typealias ProductResponse = List<ProductResponseItem>

@Serializable
data class ProductResponseItem(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
) {
    @Serializable
    data class Rating(
        val rate: Double,
        val count: Int
    )
}
