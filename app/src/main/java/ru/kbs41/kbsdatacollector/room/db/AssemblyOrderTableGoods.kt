package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders_table_goods")
data class AssemblyOrderTableGoods(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceGuid: String = "",
    val row: Int = -1,
    val qty: Double = -1.0,
    val qtyCollected: Double = -1.0,
    val assemblyOrderId: Long = -1,
    val productId: Long = -1
) {
}