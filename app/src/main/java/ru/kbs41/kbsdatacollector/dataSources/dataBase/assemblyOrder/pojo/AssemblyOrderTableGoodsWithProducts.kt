package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo

import androidx.room.Embedded
import androidx.room.Relation
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product


data class AssemblyOrderTableGoodsWithProducts(

    @Embedded val assemblyOrderTableGoods: AssemblyOrderTableGoods,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product

) {
}