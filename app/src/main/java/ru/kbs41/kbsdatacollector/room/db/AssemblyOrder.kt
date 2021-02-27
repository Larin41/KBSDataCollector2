package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "assembly_orders")
data class AssemblyOrder(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val guid: String,
    val date: Date,
    val number: String,
    val counterpart: String,
    val comment: String,
    val isCompleted: Boolean,
    val isSent: Boolean

) {
}