package dev.ajinkyajape.shoppinglist.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dev.ajinkyajape.shoppinglist.ui.screens.shopping.ShoppingList
import dev.ajinkyajape.shoppinglist.ui.theme.ShoppingListTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            ShoppingListTheme {
                Surface(
                    modifier = Modifier.wrapContentSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShoppingList()
                }
            }
        }
    }
}



