package com.example.productsappwithmvvm.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.productsappwithmvvm.data.models.Product

@Database(entities = arrayOf(Product::class), version = 1)
abstract class ProductDataBase : RoomDatabase() {
    abstract fun getProductDao(): ProductDao

    companion object {
        @Volatile
        private var INSTANCE: ProductDataBase? = null
        fun getInstance(context: Context): ProductDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDataBase::class.java,
                    "products"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}