package com.example.productsappwithmvvm.data.remote

import com.example.productsappwithmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRemoteDataSource {

    suspend fun getAllProducts(): Flow<List<Product>>?
}
