package dev.ajinkyajape.shoppinglist.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

/**
* Created By Ajinkya Jape on 09/01/2025
* */

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun instertProductItem(productModel: ProductModel)

    @Update
    suspend fun updateProductItem(productModel: ProductModel)

    @Delete
    suspend fun deleteProductItem(productModel: ProductModel)

    @Query("SELECT * FROM tbl_products")
    fun getAllProductItem() : List<ProductModel>

}