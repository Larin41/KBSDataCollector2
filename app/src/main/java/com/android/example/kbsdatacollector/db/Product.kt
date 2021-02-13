package com.android.example.kbsdatacollector.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val unit: String,
    val isAlcohol: Boolean,
    val hasStamp: Boolean,
    val guid: String,
    val isFolder: Boolean,
    val parentId: Long

) {
}