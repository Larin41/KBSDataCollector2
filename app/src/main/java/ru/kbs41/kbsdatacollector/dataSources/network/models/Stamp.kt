package ru.kbs41.kbsdatacollector.dataSources.network.models

data class Stamp(
    val guid: String,
    val stampValue: String,
    val characteristic: String?,
    val unit: String?
) {
}