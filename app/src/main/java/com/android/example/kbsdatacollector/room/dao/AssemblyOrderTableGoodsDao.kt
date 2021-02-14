package com.android.example.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.android.example.kbsdatacollector.room.db.AssemblyOrderTableGoods

@Dao
interface AssemblyOrderTableGoodsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(assemblyOrderTableGoods: AssemblyOrderTableGoods)

    @Delete
    suspend fun delete(assemblyOrderTableGoods: AssemblyOrderTableGoods)
}