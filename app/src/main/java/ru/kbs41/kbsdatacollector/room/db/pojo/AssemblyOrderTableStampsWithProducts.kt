package ru.kbs41.kbsdatacollector.room.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.room.db.Product

data class AssemblyOrderTableStampsWithProducts(

    @Embedded val assemblyOrderTableStamps: AssemblyOrderTableStamps,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product


) {
}