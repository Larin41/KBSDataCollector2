package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders_table_goods")
data class AssemblyOrderTableGoods(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    val sourceGuid: String = "",
    val row: Int = 0,
    var qty: Double = 0.0,
    var qtyCollected: Double = 0.0,
    var needStamp: Boolean = false,
    val assemblyOrderId: Long = 0,
    val productId: Long = 0
) {
}