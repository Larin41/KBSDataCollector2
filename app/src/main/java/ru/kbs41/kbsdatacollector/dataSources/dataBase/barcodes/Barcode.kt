package ru.kbs41.kbsdatacollector.dataSources.dataBase.barcodes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barcodes")
data class Barcode(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val barcode: String?,
    val productId: Long
) {


}