package ru.kbs41.kbsdatacollector.dataSources.network.models

data class TableGood(
    val productGuid: String,
    val qty: Double,
    val rowNumber: Int,
    val sourceGuid: String
)