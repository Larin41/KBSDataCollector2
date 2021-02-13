package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders_table_goods")
data class AssemblyOrderTableStamps(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val barcode: String,
    val AssemblyOrderId: Long,
    val ProductId: Long
) {
}