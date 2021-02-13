package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Stamps(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val stamp: String?,
    val productId: Long
) {


}