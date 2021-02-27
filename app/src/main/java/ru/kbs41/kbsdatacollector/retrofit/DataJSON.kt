package ru.kbs41.kbsdatacollector.retrofit

import java.sql.Date

data class DataJSON(
    val goods: List<Good>,
    val orders: List<Order>
) {
    data class Good(
        val barcodes: List<Barcode>,
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
        val tableGoods: List<TableGood>
    ) {
        data class TableGood(
            val productSourceId: String,
            val qty: Double,
            val rowNumber: Int,
            val sourceGuid: String
        )
    }
}