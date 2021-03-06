package ru.kbs41.kbsdatacollector.room.dao

import androidx.room.Dao
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps


@Dao
interface RawDao {


    @RawQuery(observedEntities = [AssemblyOrderTableGoods::class, AssemblyOrderTableStamps::class])
    fun getTableGoods(query: SupportSQLiteQuery): Flow<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>




}