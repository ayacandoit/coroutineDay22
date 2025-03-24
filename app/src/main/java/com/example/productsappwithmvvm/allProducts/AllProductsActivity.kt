package com.example.productsappwithmvvm.allProducts
import com.example.productsappwithmvvm.allProducts.AllProductsViewModel

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.productsappwithmvvm.data.local.ProductDataBase
import com.example.productsappwithmvvm.data.local.ProductLocalDataSourceImp
import com.example.productsappwithmvvm.data.models.Product
import com.example.productsappwithmvvm.data.remote.ProductRemoteDataSourceImp
import com.example.productsappwithmvvm.data.remote.RetrofitProduct
import com.example.productsappwithmvvm.data.repo.ProductRepoImp

class AllProductsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ProductsList(
                viewModel(
                    factory = AllProductsFactory(
                        ProductRepoImp.getInstance(
                            ProductRemoteDataSourceImp(RetrofitProduct.apiService),
                            ProductLocalDataSourceImp(
                                ProductDataBase.getInstance(this).getProductDao()
                            )
                        )
                    )
                )
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsList(products: AllProductsViewModel) {

    products.getProducts()
    val productList = products.products.observeAsState()
    val messageState = products.message.observeAsState()

    val snackBarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(16.dp), verticalArrangement = Arrangement.Center
        ) {
            LazyColumn {
                items(productList.value?.size ?: 0) {
                    OneProduct(
                        product = productList.value?.get(it)
                    ) { products.addToFavorite(productList.value?.get(it)) }
                }
            }
            LaunchedEffect(messageState.value) {
                if (!messageState.value.isNullOrBlank()) {
                    snackBarHostState.showSnackbar(
                        message = messageState.value.toString(),
                        duration = SnackbarDuration.Short
                    )
                }
            }
        }
    }
}

@Composable
fun OneProduct(product: Product?, addToFav: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data = product?.thumbnail)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true)
                    }).build()
            )
            Image(
                painter = painter,
                contentDescription = product?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = CircleShape),
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product?.title.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                HorizontalDivider(thickness = 2.dp)
                Text(
                    text = product?.description.toString(),
                    textAlign = TextAlign.Justify
                )
                HorizontalDivider(thickness = 2.dp)
                Button(onClick = {
                    addToFav.invoke()
                }) {
                    Text("Fav")
                }
            }
        }
    }
}
