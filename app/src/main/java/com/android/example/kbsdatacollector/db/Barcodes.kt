package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Barcodes(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val barcode: String?,
    val productId: Long
) {


}