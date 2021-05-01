package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface SimpleScanningTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(simpleScanningTableGoods: SimpleScanningTableGoods)

    @Delete
    suspend fun delete(simpleScanningTableGoods: SimpleScanningTableGoods)

    @Query("SELECT * FROM simple_scanning_table_goods WHERE simpleScanning = :docId")
    fun getByDocId(docId: Long): LiveData<List<SimpleScanningTableGoods>>

    @Query("SELECT * FROM simple_scanning_table_goods")
    suspend fun getAll(): List<SimpleScanningTableGoods>

    @Query("SELECT * FROM simple_scanning_table_goods WHERE simpleScanning = :docId AND productId = :productId LIMIT 1")
    fun getByDocAndProduct(docId: Long, productId: Long): SimpleScanningTableGoods?


}