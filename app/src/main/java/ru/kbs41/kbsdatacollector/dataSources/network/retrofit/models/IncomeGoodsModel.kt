package ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models

data class IncomeGoodsModel(
    val goods: List<Good>,
    val result: String
) {

    data class Good(
        val guid: String,
        val hasStamp: Boolean,
        val isAlcohol: Boolean,
        val isFolder: Boolean,
        val name: String,
        val parentGuid: String,
        val barcodes: List<Barcode>?,
        val stamps: List<Stamp>?,
        val stampsEGAIS: List<StampEGAIS>?,
        val unit: String?
    ) {
        data class Barcode(
            val barcode: String
        )

        data class Stamp(
            val stamp: String
        )

        data class StampEGAIS(
            val stamp: String
        )
    }
}
