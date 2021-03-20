package ru.kbs41.kbsdatacollector.room.db.pojo

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

class AssemblyOrderTableGoodsWithQtyCollectedAndProducts(
        val id: Long?,
        val row: Int?,
        val orderID: Long?,
        val productName: String?,
        val productHasStamps: Boolean?,
        val productId: Long?,
        val qty: Double?,
        val qtyCollected: Double?

) {
}