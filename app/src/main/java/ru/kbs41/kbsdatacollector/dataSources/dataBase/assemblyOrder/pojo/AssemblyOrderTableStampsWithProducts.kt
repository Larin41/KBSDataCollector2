package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo

import androidx.room.Embedded
import androidx.room.Relation
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product

data class AssemblyOrderTableStampsWithProducts(

    @Embedded val assemblyOrderTableStamps: AssemblyOrderTableStamps,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product


) {
}