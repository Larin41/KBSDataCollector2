package ru.kbs41.kbsdatacollector.dataSources.network.downloaders

import android.widget.ProgressBar
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.network.models.Contractor

object ContractorsDownloader {

    val database = App().database
    val contractorDao = database.contractorDao()

    fun downloadContractors(contractors: List<Contractor>, progressBar: ProgressBar? = null) {

        contractors.forEach {
            donwloadContractor(it)
        }


    }

    private fun donwloadContractor(contractor: Contractor) {


        var newContractor = contractorDao.findContractorByGuid(contractor.guid)
        if (newContractor == null) {
            newContractor = ru.kbs41.kbsdatacollector.dataSources.dataBase.contractors.Contractor()
        }

        newContractor?.let {
            it.guid = contractor.guid
            it.inn = contractor.inn
            it.kpp = contractor.kpp
            it.name = contractor.name

            contractorDao.insert(it)
        }

    }

}