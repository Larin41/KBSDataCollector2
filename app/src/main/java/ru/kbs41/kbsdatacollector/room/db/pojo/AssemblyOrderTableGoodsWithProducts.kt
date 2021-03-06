package ru.kbs41.kbsdatacollector.room.db.pojo

import androidx.room.Embedded
import androidx.room.Relation
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.Product


data class AssemblyOrderTableGoodsWithProducts(

    @Embedded val assemblyOrderTableGoods: AssemblyOrderTableGoods,

    @Relation(
        parentColumn = "productId",
        entityColumn = "id"
    )
    val product: Product

) {
}