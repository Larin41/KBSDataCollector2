package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders_table_stamps")
data class AssemblyOrderTableStamps(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val barcode: String,
    var assemblyOrderId: Long,
    val productId: Long
) {
}