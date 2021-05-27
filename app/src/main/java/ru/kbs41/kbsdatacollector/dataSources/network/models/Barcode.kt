package ru.kbs41.kbsdatacollector.dataSources.network.models

data class Barcode(
    val barcode: String,
    val characteristic: String?,
    val unit: String?
) {
}