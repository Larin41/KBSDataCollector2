package ru.kbs41.kbsdatacollector.dataSources.network.models

data class Good(
    val barcodes: List<Barcode>?,
    val baseUnit: String?,
    val characteristics: List<String>?,
    val folderGuid: String,
    val guid: String,
    val hasStamp: Boolean?,
    val image: String?,
    val isAlcohol: Boolean?,
    val isFolder: Boolean?,
    val name: String,
    val stamps: List<Stamp>?,
    val stampsEGAIS: List<Any>?,
    val units: List<Any>?
)