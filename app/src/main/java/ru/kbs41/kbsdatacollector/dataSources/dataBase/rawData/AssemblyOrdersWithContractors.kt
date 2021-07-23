package ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData

import org.w3c.dom.Comment
import java.util.*

data class AssemblyOrdersWithContractors(
    val id: Long,
    val date: Date,
    val number: String,
    val comment: String,
    val contractor: String
) {
}