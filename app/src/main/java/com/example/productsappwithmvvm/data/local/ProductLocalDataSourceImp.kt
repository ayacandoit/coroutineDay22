package com.example.productsappwithmvvm.data.local

import com.example.productsappwithmvvm.data.models.Product
import kotlinx.coroutines.flow.Flow

class ProductLocalDataSourceImp(private val dao: ProductDao) : ProductLocalDataSource {

    override suspend fun getFavProducts(): Flow<List<Product>> {
        return dao.getFavoriteProducts()
    }

    override suspend fun insertProduct(product: Product): Long {
        return dao.insertProduct(product)
    }

    override suspend fun deleteProduct(product: Product?): Int {
        return if (product != null)
            dao.deleteProduct(product)
        else
            -1

    }

}
