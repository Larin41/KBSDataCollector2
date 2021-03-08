package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "assembly_orders")
data class AssemblyOrder(
    @PrimaryKey(autoGenerate = true) var id: Long = -1,
    val guid: String = "",
    val date: Date = Date(0),
    val number: String = "",
    val counterpart: String = "",
    val comment: String = "",
    val isCompleted: Boolean = false,
    val isSent: Boolean = false
) {

}