package dev.ajinkyajape.shoppinglist.ui.screens.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// UI for an alert dialog to show the user when they click on the Edit icon
@Composable
fun ShoppingItemEditor(
    shoppingItems: ShoppingItems, onEditComplete: (String, Int) -> Unit
) {
    var editItemName by remember { mutableStateOf(shoppingItems.itemName) }
    var editItemQty by remember { mutableStateOf(shoppingItems.iQty.toString()) }
    var isEditing by remember { mutableStateOf(shoppingItems.isEditable) }


    Card(modifier = Modifier.padding(5.dp)

        .wrapContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
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

            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {

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
    }
}