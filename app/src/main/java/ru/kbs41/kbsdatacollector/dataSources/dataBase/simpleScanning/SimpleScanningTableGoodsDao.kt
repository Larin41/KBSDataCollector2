package ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning

import androidx.room.*
import ru.kbs41.kbsdatacollector.dataSources.dataBase.settings.Settings

@Dao
interface SimpleScanningTableGoodsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(simpleScanningTableGoods: SimpleScanningTableGoods)

    @Delete
    suspend fun delete(simpleScanningTableGoods: SimpleScanningTableGoods)

    @Query("SELECT * FROM simple_scanning_table_goods")
    suspend fun getAll(): List<SimpleScanningTableGoods>

}