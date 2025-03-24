package com.example.productsappwithmvvm.data.remote

import com.example.productsappwithmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProductRemoteDataSourceImp(private val service: ProductService) : ProductRemoteDataSource {
    override suspend fun getAllProducts(): Flow<List<Product>>? {
        val response = service.getProducts().body()
        return response?.products?.let { flow { emit(it) } }
    }
}
