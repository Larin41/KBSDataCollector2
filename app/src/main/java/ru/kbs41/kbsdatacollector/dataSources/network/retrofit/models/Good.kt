package ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models

data class Good(
    val barcodes: List<Barcode>,
    val guid: String,
    val hasStamp: Boolean,
    val isAlcohol: Boolean,
    val isFolder: Boolean,
    val name: String,
    val parentGuid: String,
    val stamps: List<Any>,
    val stampsEGAIS: List<Any>,
    val unit: String
)