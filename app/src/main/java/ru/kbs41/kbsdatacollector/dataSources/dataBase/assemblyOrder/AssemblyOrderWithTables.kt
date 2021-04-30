package ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder

import androidx.room.Embedded
import androidx.room.Relation

data class AssemblyOrderWithTables(
    @Embedded val assemblyOrder: AssemblyOrder,

    @Relation(
        parentColumn = "id",
        entityColumn = "assemblyOrderId"
    )
    val tableGoods: List<AssemblyOrderTableGoods>,


    @Relation(
        parentColumn = "id",
        entityColumn = "assemblyOrderId"
    )
    val tableStamps: List<AssemblyOrderTableStamps>

    ) {
}