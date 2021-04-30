package ru.kbs41.kbsdatacollector.dataSources.dataBase.stamps

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stamps")
data class Stamp(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val stamp: String?,
    val productId: Long
) {


}