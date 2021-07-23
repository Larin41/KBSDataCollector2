package ru.kbs41.kbsdatacollector.dataSources.dataBase.contractors

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contractors")
data class Contractor(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var guid: String = "",
    var name: String = "",
    var inn: String = "",
    var kpp: String = ""
) {


}