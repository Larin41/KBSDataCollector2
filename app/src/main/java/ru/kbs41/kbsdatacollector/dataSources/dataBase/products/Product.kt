package ru.kbs41.kbsdatacollector.dataSources.dataBase.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var unit: String? = "",
    var isAlcohol: Boolean? = false,
    var hasStamp: Boolean? = false,
    var guid: String? = "",
    var isFolder: Boolean? = false,
    var parentGuid: String? = "",
    var parentId: Long? = 0
) {





}