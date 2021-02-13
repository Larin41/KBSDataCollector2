package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssemblyOrderTableGoods(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val sourceGuid: String,
    val row: Int,
    val qty: Double,
    val qtyCollected: Double,
    val AssemblyOrderId: Long,
    val ProductId: Long
) {
}