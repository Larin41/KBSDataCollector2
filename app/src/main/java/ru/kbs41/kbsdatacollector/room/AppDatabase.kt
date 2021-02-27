package ru.kbs41.kbsdatacollector.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.room.dao.*
import ru.kbs41.kbsdatacollector.room.db.*

@Database(
    entities = [
        AssemblyOrder::class,
        AssemblyOrderTableGoods::class,
        AssemblyOrderTableStamps::class,
        Barcode::class,
        Product::class,
        Stamp::class
    ], version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assemblyOrderDao(): AssemblyOrderDao
    abstract fun assemblyOrderTableGoodsDao(): AssemblyOrderTableGoodsDao
    abstract fun assemblyOrderTableStampsDao(): AssemblyOrderTableStampsDao
    abstract fun barcodeDao(): BarcodeDao
    abstract fun productDao(): ProductDao
    abstract fun stampDao(): StampDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dc_database"
                )
                    .addCallback(ProductDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance

            }
        }

        private class ProductDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch {
                        populateDatabase(database.productDao())
                    }
                }
            }

            suspend fun populateDatabase(productDao: ProductDao) {

            }
        }

    }
}