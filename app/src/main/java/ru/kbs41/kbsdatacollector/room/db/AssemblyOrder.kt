package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "assembly_orders")
data class AssemblyOrder(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var guid: String = "",
    var date: Date = Date(0),
    var number: String = "",
    var counterpart: String = "",
    var comment: String = "",
    var isCompleted: Boolean = false,
    var isSent: Boolean = false
) {

}