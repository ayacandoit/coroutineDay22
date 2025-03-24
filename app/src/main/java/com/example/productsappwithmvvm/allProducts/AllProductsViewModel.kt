package com.example.productsappwithmvvm.allProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.productsappwithmvvm.data.models.Product
import com.example.productsappwithmvvm.data.repo.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: ProductRepo) : ViewModel() {

    private val mutableProducts: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    val products = mutableProducts.asStateFlow()

    private val mutableMessage: MutableStateFlow<String> = MutableStateFlow("")
    val message = mutableMessage.asStateFlow()

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllProducts(true)
                if (result != null) {
                    val list: List<Product> = result
                    mutableProducts.postValue(list)
                } else {
                    mutableMessage.postValue("Please try again later...")
                }
            } catch (ex: Exception) {
                mutableMessage.postValue("An error occurred ${ex.message}")
            }
        }
    }

    fun addToFavorite(product: Product?) {
        if (product != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val result = repo.addProduct(product)
                    if (result > 0) {
                        mutableMessage.postValue("Added Successfully")
                    } else {
                        mutableMessage.postValue("Product is already in the favours")
                    }
                } catch (ex: Exception) {
                    mutableMessage.postValue("couldn't add...")
                }
            }
        } else {
            mutableMessage.postValue("couldn't add...")
        }
    }
}

class AllProductsFactory(private val repo: ProductRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AllProductsViewModel(repo) as T
    }

}
