package dev.ajinkyajape.shoppinglist.ui.screens.shopping

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly

/**
 * Created By Ajinkya Jape on 26/12/2024
 * */

data class ShoppingItems(
    val id: Int, var itemName: String, var iQty: Int, var isEditable: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingList() {
    var shoppingItemsList by remember { mutableStateOf(listOf<ShoppingItems>()) }
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }
    var isShowDialog by remember { mutableStateOf(false) }

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { isShowDialog = true }) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
        ) {


            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
            ) {
                TopAppBar(
                    title = {
                        Text(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            text = "Shopping List"
                        )
                    }
                )
            }


            Box(modifier = Modifier.padding(paddingValues)) {

                if (shoppingItemsList.isEmpty()) {
                    Text(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "No Items"
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(shoppingItemsList) { itemValue ->
                            if (itemValue.isEditable) {
                                ShoppingItemEditor(shoppingItems = itemValue,
                                    onEditComplete = { editName, editQty ->
                                        shoppingItemsList =
                                            shoppingItemsList.map { it.copy(isEditable = false) }
                                        val isEditableItem =
                                            shoppingItemsList.find { it.id == itemValue.id }
                                        isEditableItem?.let {
                                            it.itemName = editName
                                            it.iQty = editQty
                                        }

                                    }
                                )
                            } else {
                                ShoppingListItems(
                                    item = itemValue,
                                    onEditClick = {
                                        shoppingItemsList =
                                            shoppingItemsList.map { it.copy(isEditable = it.id == itemValue.id) }
                                    },
                                    onDeleteClick = {
                                        shoppingItemsList = shoppingItemsList - itemValue
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }


    }

    if (isShowDialog) {
        itemQty = "1"
        AlertDialog(
            onDismissRequest = { isShowDialog = false },
            title = { Text("Add Shopping Item") },
            text = {
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,

                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        )
                    )

                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        value = itemQty,
                        onValueChange = { newValue ->
                            itemQty = newValue.filter { newValue.isDigitsOnly() }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType =
                            KeyboardType.Number
                        ),
                        singleLine = true,
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (itemName.isNotBlank() && itemQty.isNotEmpty()) {
                            val newItem = ShoppingItems(
                                id = shoppingItemsList.size + 1,
                                itemName = itemName,
                                iQty = itemQty.toInt()
                            )
                            shoppingItemsList = shoppingItemsList + newItem
                            isShowDialog = false
                            itemName = ""
                        }
                    }
                ) {
                    Text("Add")
                }
            }, dismissButton = {
                Button(
                    onClick = { isShowDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}


@Preview
@Composable
fun ShoppingListPreview() {
    ShoppingList()
}