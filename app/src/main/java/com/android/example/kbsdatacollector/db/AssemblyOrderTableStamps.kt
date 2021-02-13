package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AssemblyOrderTableStamps(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val barcode: String,
    val AssemblyOrderId: Long,
    val ProductId: Long
) {
}