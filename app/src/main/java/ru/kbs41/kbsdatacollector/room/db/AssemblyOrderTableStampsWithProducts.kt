package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Embedded
import androidx.room.Relation

data class AssemblyOrderTableStampsWithProducts(

    @Embedded val assemblyOrderTableStamps: AssemblyOrderTableStamps,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product


) {
}