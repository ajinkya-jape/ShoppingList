package dev.ajinkyajape.shoppinglist.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.ajinkyajape.shoppinglist.room.ProductAction
import dev.ajinkyajape.shoppinglist.room.ProductDatabase
import dev.ajinkyajape.shoppinglist.room.ProductModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Created by Ajinkya Jape on 09/01/25.
 */

class ProductViewModel(context: Context) : ViewModel() {
    private val productDao = ProductDatabase.getDatabase(context = context).productDao()
    private val productMainList = MutableStateFlow<List<ProductModel>>(emptyList())
    val productList: StateFlow<List<ProductModel>> = productMainList.asStateFlow()


    fun getAllProducts() {
        viewModelScope.launch {
            productMainList.value = productDao.getAllProductItem()
        }
    }

    fun addProduct(pItem: String, pQty: Int) {
        viewModelScope.launch {
            productDao.instertProductItem(
                ProductModel(
                    productName = pItem,
                    productQty = pQty
                )
            )
        }
    }

    fun updateOrDeleteProduct(pItem: ProductModel,productAction: ProductAction) {
        viewModelScope.launch {
            when(productAction){
                ProductAction.UPDATE->{
                    productDao.updateProductItem(pItem)
                }
                ProductAction.DELETE->{
                    productDao.deleteProductItem(pItem)
                }
            }
            getAllProducts()
        }
    }
}