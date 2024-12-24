package dev.ajinkyajape.shoppinglist

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly

data class ShoppingItems(
    val id: Int, var itemName: String, var iQty: Int, var isEditable: Boolean = false
)

@Composable
fun ShoppingList() {
    var shoppingItemsList by remember { mutableStateOf(listOf<ShoppingItems>()) }
    var itemName by remember { mutableStateOf("") }
    var itemQty by remember { mutableStateOf("") }
    var isShowDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center
    ) {

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { isShowDialog = true }
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            }
        ) { paddingValues ->

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
                            .padding(15.dp)
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
                        value = itemName,
                        onValueChange = { itemName = it },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    OutlinedTextField(
                        value = itemQty,
                        onValueChange = { newValue ->
                            itemQty = newValue.filter { newValue.isDigitsOnly() }
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType =
                            KeyboardType.Number
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            },
            confirmButton = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
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
                    Button(
                        onClick = { isShowDialog = false }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        )
    }
}

//Represent List of Items
@Composable
fun ShoppingListItems(
    item: ShoppingItems, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(
                border = BorderStroke(2.dp, Color(0XFF018786)), shape = RoundedCornerShape(20)
            )

    ) {

        Column {

            Text(
                text = "Item: ${item.itemName}",
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(text = "Qty:${item.iQty}", modifier = Modifier.padding(8.dp))
        }

        Row(
            modifier = Modifier.padding(10.dp),

            ) {

            IconButton(onClick = onEditClick) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null)
            }
            IconButton(onClick = onDeleteClick) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = null)
            }
        }
    }
}


// UI for an alert dialog to show the user when they click on the Edit icon
@Composable
fun ShoppingItemEditor(
    shoppingItems: ShoppingItems, onEditComplete: (String, Int) -> Unit
) {
    var editItemName by remember { mutableStateOf(shoppingItems.itemName) }
    var editItemQty by remember { mutableStateOf(shoppingItems.iQty.toString()) }
    var isEditing by remember { mutableStateOf(shoppingItems.isEditable) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column {

            BasicTextField(
                value = editItemName,
                onValueChange = { editItemName = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )

            BasicTextField(
                value = editItemQty,
                onValueChange = { editItemQty = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(8.dp)
            )
        }

        IconButton(onClick = {
            isEditing = false
            onEditComplete(editItemName, editItemQty.toIntOrNull() ?: 1)
        }) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }

        IconButton(onClick = {
            isEditing = false
            onEditComplete(editItemName, editItemQty.toIntOrNull() ?: 1)
        }) {
            Icon(imageVector = Icons.Default.Close, contentDescription = null)
        }
    }

}

@Preview
@Composable
fun ShoppingListPreview() {
    ShoppingList()
}