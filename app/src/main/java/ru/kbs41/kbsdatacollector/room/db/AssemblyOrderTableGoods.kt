package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders_table_goods")
data class AssemblyOrderTableGoods(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val sourceGuid: String,
    val row: Int,
    val qty: Double,
    val qtyCollected: Double,
    val assemblyOrderId: Long,
    val productId: Long
) {
}