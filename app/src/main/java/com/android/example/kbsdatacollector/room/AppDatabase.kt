package com.android.example.kbsdatacollector.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.example.kbsdatacollector.room.dao.*
import com.android.example.kbsdatacollector.room.db.*

@Database(
    entities = arrayOf(
        AssemblyOrder::class,
        AssemblyOrderTableGoods::class,
        AssemblyOrderTableStamps::class,
        Barcode::class,
        Product::class,
        Stamp::class
    ), version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assemblyOrderDao(): AssemblyOrderDao
    abstract fun assemblyOrderTableGoodsDao(): AssemblyOrderTableGoodsDao
    abstract fun assemblyOrderTableStampsDao(): AssemblyOrderTableStampsDao
    abstract fun barcodeDao(): BarcodeDao
    abstract fun productDao(): ProductDao
    abstract fun stampDao(): StampDao

    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "dc_datebase"
            ).build()
            INSTANCE = instance
            // return instance
            instance

        }

    }
}