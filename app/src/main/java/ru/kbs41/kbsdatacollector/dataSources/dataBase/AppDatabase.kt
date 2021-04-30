package ru.kbs41.kbsdatacollector.dataSources.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.*
import ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes.Barcode
import ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes.BarcodeDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.ProductDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData.RawDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.SettingsDao
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.Stamp
import ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps.StampDao

@Database(
    entities = [
        AssemblyOrder::class,
        AssemblyOrderTableGoods::class,
        AssemblyOrderTableStamps::class,
        Barcode::class,
        Product::class,
        Stamp::class,
        Settings::class
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
    abstract fun rawDao(): RawDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context,  scope: CoroutineScope?): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dc_database.db"
                )
                    .allowMainThreadQueries() //TODO: удалить перед публикацией
                    .build()
                INSTANCE = instance
                // return instance
                instance

            }
        }
    }
}