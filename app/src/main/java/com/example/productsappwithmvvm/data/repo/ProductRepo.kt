package com.example.productsappwithmvvm.data.repo

import com.example.productsappwithmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepo {

    suspend fun getAllProducts(isOnline: Boolean): Flow<List<Product>>?

    suspend fun addProduct(product: Product): Long

    suspend fun removeProduct(product: Product): Int
}
