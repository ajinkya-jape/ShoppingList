package dev.ajinkyajape.shoppinglist.ui.screens.shopping

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//Represent List of Items
@Composable
fun ShoppingListItems(
    item: ShoppingItems, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {
    Card(modifier = Modifier.padding(5.dp)
        .background(color = Color.White)
        .wrapContentSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {

            Column {
                Text(
                    text = "Item: ${item.itemName}",
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(text = "Qty:${item.iQty}",
                    fontSize = 13.sp,
                    modifier = Modifier.padding(8.dp))
            }

            Row(
                modifier = Modifier.padding(8.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
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


}