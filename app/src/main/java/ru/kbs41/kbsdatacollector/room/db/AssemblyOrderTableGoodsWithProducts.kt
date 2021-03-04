package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Embedded
import androidx.room.Relation

data class AssemblyOrderTableGoodsWithProducts(

    @Embedded val assemblyOrderTableGoods: AssemblyOrderTableGoods,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product

) {
}