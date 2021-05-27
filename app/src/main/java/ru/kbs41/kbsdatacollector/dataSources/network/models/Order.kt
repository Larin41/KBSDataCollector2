package ru.kbs41.kbsdatacollector.dataSources.network.models

import java.util.*

data class Order(
    val comment: String,
    val contractor: String,
    val date: Date,
    val guid: String,
    val isCompleted: Boolean?,
    val number: String,
    val tableGoods: List<TableGood>?
)