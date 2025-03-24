package com.example.productsappwithmvvm

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.productsappwithmvvm.allProducts.AllProductsActivity
import com.example.productsappwithmvvm.favProducts.FavActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Composable
    fun MainScreen() {

        Column {
            Button(onClick = {
                val intent = Intent(this@MainActivity, AllProductsActivity::class.java)
                startActivity(intent)
            }) {
                Text("All Products")
            }

            Button(onClick = {
                val intent = Intent(this@MainActivity, FavActivity::class.java)
                startActivity(intent)
            }) {
                Text("Favorite Products")
            }

            Button(onClick = {}) {
                Text("Exit")
            }
        }

    }

}


