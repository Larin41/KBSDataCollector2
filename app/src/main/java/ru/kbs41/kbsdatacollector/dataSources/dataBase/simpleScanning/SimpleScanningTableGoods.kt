package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "simple_scanning_table_goods")
data class SimpleScanningTableGoods(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val sourceGuid: String = "",
    var qty: Double = 0.0,
    val simpleScanning: Long = 0,
    val productId: Long = 0
) {
}