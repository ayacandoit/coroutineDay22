package com.example.productsappwithmvvm.data.repo

import com.example.productsappwithmvvm.data.local.ProductLocalDataSourceImp
import com.example.productsappwithmvvm.data.models.Product
import com.example.productsappwithmvvm.data.remote.ProductRemoteDataSourceImp
import kotlinx.coroutines.flow.Flow

class ProductRepoImp private constructor(
    private val remoteDataSource: ProductRemoteDataSourceImp,
    private val localDataSource: ProductLocalDataSourceImp,
) : ProductRepo {
    override suspend fun getAllProducts(isOnline: Boolean): Flow<List<Product>>? {
        return if (isOnline) {
            remoteDataSource.getAllProducts()
        } else {
            localDataSource.getFavProducts()
        }
    }

    override suspend fun addProduct(product: Product): Long {
        return localDataSource.insertProduct(product)
    }

    override suspend fun removeProduct(product: Product): Int {
        return localDataSource.deleteProduct(product)
    }

    companion object {
        @Volatile
        private var INSTANCE: ProductRepoImp? = null
        fun getInstance(
            remoteDataSource: ProductRemoteDataSourceImp,
            localDataSource: ProductLocalDataSourceImp,
        ): ProductRepoImp {
            return INSTANCE ?: synchronized(this) {
                val instance = ProductRepoImp(remoteDataSource, localDataSource)
                INSTANCE = instance
                instance
            }
        }
    }

}
