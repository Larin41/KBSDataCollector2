package ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models

import java.sql.Date

data class IncomeDataOrders(
    val result: String?,
    val goods: List<Good>?,
    val orders: List<Order>?
) {
    data class Good(
        val barcodes: List<Barcode>?,
        val guid: String,
        val hasStamp: Boolean,
        val isAlcohol: Boolean,
        val isFolder: Boolean,
        val name: String,
        val parentGuid: String,
        val stamps: List<Stamp>,
        val stampsEGAIS: List<StampsEGAIS>,
        val unit: String
    ) {
        data class Barcode(
            val barcode: String
        )

        data class Stamp(
            val stamp: String
        )

        data class StampsEGAIS(
            val stampEGAIS: String
        )
    }

    data class Order(
        val comment: String,
        val completed: Boolean,
        val contractor: String,
        val date: Date,
        val guid: String,
        val number: String,
        val tableGoods: List<TableGood>,
        val tableStamps: List<TableStamps>
    ) {
        data class TableGood(
            val sourceGuid: String,
            val productSourceId: String,
            val qty: Double,
            val rowNumber: Int
        )

        data class TableStamps(
            val sourceGuid: String,
            val rowNumber: Int,
            val productSourceId: String,
            val stamp: String
        )

    }
}