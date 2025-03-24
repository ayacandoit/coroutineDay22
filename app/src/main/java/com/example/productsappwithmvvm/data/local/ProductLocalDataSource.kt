package com.example.productsappwithmvvm.data.local

import com.example.productsappwithmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductLocalDataSource {

    suspend fun getFavProducts(): Flow<List<Product>>

    suspend fun insertProduct(product: Product): Long

    suspend fun deleteProduct(product: Product?): Int
}
