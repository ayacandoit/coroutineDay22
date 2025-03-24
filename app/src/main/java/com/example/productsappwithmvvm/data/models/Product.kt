package com.example.productsappwithmvvm.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "producttable")
@Parcelize
class Product(
    @PrimaryKey
    var id: Long? = null,

    var title: String? = null,
    var price: Double = 0.0,
    var brand: String? = null,
    var description: String? = null,
    var rating: Double = 0.0,
    var thumbnail: String? = null,
) : Parcelable
