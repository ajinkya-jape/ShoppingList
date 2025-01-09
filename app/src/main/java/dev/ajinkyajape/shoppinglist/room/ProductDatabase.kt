package dev.ajinkyajape.shoppinglist.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

/**
 * Created by Ajinkya Jape on 09/01/25.
 */

@Database(entities = [ProductModel::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{

        @Volatile
        private  var INSTANCE : ProductDatabase? = null

        fun getDatabase(context: Context):ProductDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    "product_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}