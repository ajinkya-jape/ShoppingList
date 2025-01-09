package dev.ajinkyajape.shoppinglist.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Created By Ajinkya Jape - 08/01/2025
* */

@Entity("tbl_products")
data class ProductModel (
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,
    var productName: String,
    var productQty: Int,
    var isEditable: Boolean = false
)