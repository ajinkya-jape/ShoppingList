package dev.ajinkyajape.shoppinglist.ui.screens.shopping

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import dev.ajinkyajape.shoppinglist.viewmodel.ProductViewModel

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.ajinkyajape.shoppinglist.room.ProductAction

/**
 * Created by Ajinkya Jape on 09/01/25.
 */

@Composable
fun NewShoppingList(viewModel: ProductViewModel) {

    val shoppingList by viewModel.productList.collectAsState()

    var itemName by remember { mutableStateOf("") }
    var itemQuantity by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(
            value = itemName,
            onValueChange = { itemName = it },
            label = { Text("Item Name") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = itemQuantity,
            onValueChange = { itemQuantity = it },
            label = { Text("Quantity") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text
            )
        )

        Button(
            onClick = {
                if (itemName.isNotEmpty() && itemQuantity.isNotEmpty()) {
                    viewModel.addProduct(itemName, itemQuantity.toInt())
                    itemName = ""
                    itemQuantity = ""
                }
            },
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Add Item")
        }

        LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
            items(shoppingList) { item ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("${item.productName} (${item.productQty})")
                    Row {
                        Button(onClick = {
                            viewModel.updateOrDeleteProduct(
                                item,
                                ProductAction.DELETE
                            )
                        }) {
                            Text("Delete")
                        }
                        Button(onClick = {
                            viewModel.updateOrDeleteProduct(
                                item.copy(productQty = item.productQty + 1),
                                ProductAction.UPDATE
                            )
                        }) {
                            Text("+1")
                        }
                    }
                }
            }
        }
    }
}