package ru.kbs41.kbsdatacollector.dataSources.dataBase.contractors

import androidx.room.*
import ru.kbs41.kbsdatacollector.dataSources.dataBase.contractors.Contractor


@Dao
interface ContractorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contractor: Contractor)

    @Delete
    suspend fun delete(contractor: Contractor)

    @Query("SELECT * FROM contractors WHERE guid = :guid")
    fun findContractorByGuid(guid: String): Contractor?

}