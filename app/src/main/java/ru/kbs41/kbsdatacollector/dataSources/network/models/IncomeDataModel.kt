package ru.kbs41.kbsdatacollector.dataSources.network.models

data class IncomeDataModel(
    val contractors: List<Contractor>?,
    val goods: List<Good>?,
    val orders: List<Order>?,
    val result: String?
)