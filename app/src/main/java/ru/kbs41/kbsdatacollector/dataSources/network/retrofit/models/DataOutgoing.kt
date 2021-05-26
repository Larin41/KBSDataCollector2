package ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models

import org.w3c.dom.Comment
import java.util.*


data class DataOutgoing(
    val type: String,
    val order: OrderModel
) {

    //ТАБЛИЦА ЗАКАЗЫ НА СБОРКУ
    data class OrderModel(
        val guid: String,
        val date: Date,
        val number: String,
        val comment: String?,
        val tableGoods: List<TableGoodsModel>?,
        val tableStamps: List<TableStampsModel>?
    ) {

        //ТАБЛИЦА ТОВАРЫ
        data class TableGoodsModel(
            val productName: String,
            val productGuid: String,
            val qty: Double,
            val qtyCollected: Double
        ) {}

        //ТАБЛИЦА МАРКИ
        data class TableStampsModel(
            val productGuid: String,
            val stamp: String,
        ) {}

    }

}