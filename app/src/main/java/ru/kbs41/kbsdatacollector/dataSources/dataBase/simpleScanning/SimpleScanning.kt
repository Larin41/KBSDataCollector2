package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "simple_scanning")
data class SimpleScanning(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var guid: String = "",
    var date: Date = Date(0),
    var comment: String = "",
    var isCompleted: Boolean = false,
    var isSent: Boolean = false
) {

}