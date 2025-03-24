package com.example.productsappwithmvvm.favProducts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.productsappwithmvvm.data.models.Product
import com.example.productsappwithmvvm.data.repo.ProductRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FavoriteProductsViewModel(private val repo: ProductRepo) : ViewModel() {

    private val mutableProducts: MutableLiveData<List<Product>> = MutableLiveData()
    val products: LiveData<List<Product>> = mutableProducts

    private val mutableMessage: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = mutableMessage

    fun getFavProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.getAllProducts(false)
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

    fun removeFromFavorite(product: Product?) {
        if (product != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = repo.removeProduct(product)
                if (result > 0) {
                    mutableMessage.postValue("removed Successfully")
                    getFavProducts()
                } else {
                    mutableMessage.postValue("Product isn't in the favours")
                }
            }
        } else {
            mutableMessage.postValue("couldn't add...")
        }
    }
}

class FavProductsFactory(private val repo: ProductRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavoriteProductsViewModel(repo) as T
    }
}