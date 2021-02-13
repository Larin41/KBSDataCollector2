package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assembly_orders")
data class AssemblyOrder(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val guid: String,
    val date: String,
    val number: String,
    val counterpart: String,
    val comment: String,
    val isCompleted: Boolean,
    val isSent: Boolean

) {
}