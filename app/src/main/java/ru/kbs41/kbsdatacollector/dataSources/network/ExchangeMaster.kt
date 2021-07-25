package ru.kbs41.kbsdatacollector.dataSources.network


import android.app.Application
import android.os.Debug
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import kotlinx.coroutines.*
import retrofit2.*
import ru.kbs41.kbsdatacollector.notificationManager.AppNotificationManager
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.IncomeDataOrders
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.SendingStatus
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.dataSources.network.downloaders.AssemblyOrderDownloader
import ru.kbs41.kbsdatacollector.dataSources.network.downloaders.ContractorsDownloader
import ru.kbs41.kbsdatacollector.dataSources.network.downloaders.GoodsDownloader
import ru.kbs41.kbsdatacollector.dataSources.network.models.Contractor
import ru.kbs41.kbsdatacollector.dataSources.network.models.IncomeDataModel
import ru.kbs41.kbsdatacollector.dataSources.network.models.Order
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.RetrofitClient
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.IncomeGoodsModel
import ru.kbs41.kbsdatacollector.dataSources.network.senders.AssemblyOrderSender
import ru.kbs41.kbsdatacollector.dataSources.network.senders.SimpleScanningSender
import java.io.IOException

object ExchangeMaster {

    fun getAllGoodsFrom1C(progressBar: ProgressBar? = null) {

        //Debug.waitForDebugger()

        val settings = mapOf(
            "deviceId" to "1",
            "requiredData" to "allGoods"
        )

        try {
            getData(settings, progressBar)
        } catch (e: IOException) {
            Log.d("1C_TO_APP", e.message!!)
        } finally {
            //DO NOTHING
        }

    }

    fun getOrdersFrom1C(application: Application) {

        val settings = mapOf(
            "deviceId" to "1",
            "requiredData" to "newData"
        )

        try {
            getData(settings)
        } catch (e: IOException) {
            Log.d("GoodsFrom1C", e.message!!)
        } finally {
            //DO NOTHING
        }
    }

    private fun getData(settings: Map<String, String>, progressBar: ProgressBar? = null) {

        //Debug.waitForDebugger()
        val retrofit = RetrofitClient()

        retrofit.initInstance()

        if (settings["requiredData"]!! == "newData") {
            getNewOrders(retrofit, settings)
        }

        if (settings["requiredData"]!! == "allGoods") {
            getAllGoods(retrofit, settings, progressBar)
        }


    }

    private fun getAllGoods(
        retrofit: RetrofitClient,
        settings: Map<String, String>,
        progressBar: ProgressBar? = null
    ) {
        retrofit.instance.getGoods(settings["deviceId"]!!, settings["requiredData"]!!)
            ?.enqueue(object : Callback<IncomeGoodsModel> {
                override fun onResponse(
                    call: Call<IncomeGoodsModel>,
                    response: Response<IncomeGoodsModel>
                ) {
                    GlobalScope.launch(Dispatchers.IO) {

                        //Debug.waitForDebugger()
                        val body: IncomeGoodsModel? = response.body()
                        if (body == null) {
                            Log.d("GoodsFrom1C", "Null body")
                            return@launch
                        }

                        Log.d("GoodsFrom1C", body.result)
                        Log.d("GoodsFrom1C", response.isSuccessful.toString())
                        downloadGoods(body, progressBar)

                    }
                }

                override fun onFailure(call: Call<IncomeGoodsModel>, t: Throwable) {
                    Log.d("GoodsFrom1C", "Couldn't download data")
                }
            })
    }

    private fun downloadGoods(body: IncomeGoodsModel?, progressBar: ProgressBar? = null) {
        //Debug.waitForDebugger()


        GlobalScope.launch(Dispatchers.Main) { progressBar?.visibility = View.GONE }

    }

    private fun getNewOrders(retrofit: RetrofitClient, settings: Map<String, String>) {
        retrofit.instance.getOrders(settings["deviceId"]!!, settings["requiredData"]!!)
            ?.enqueue(object : Callback<IncomeDataModel> {
                override fun onResponse(
                    call: Call<IncomeDataModel>,
                    response: Response<IncomeDataModel>
                ) {
                    GlobalScope.launch(Dispatchers.IO) {

                        //Debug.waitForDebugger()

                        val body: IncomeDataModel? = response.body()
                        if (body == null) {
                            Log.d("1C_TO_APP", "Null body")
                            return@launch
                        }

                        body.goods?.let { goods ->
                            GoodsDownloader.downloadCatalogs(body.goods)
                        }

                        body.contractors?.let { contractors ->
                            downloadContractors(body.contractors)
                        }

                        body.orders?.let { orders ->
                            downloadNewOrders(body.orders)
                        }


                    }
                }

                override fun onFailure(call: Call<IncomeDataModel>, t: Throwable) {
                    Log.d("1C_TO_APP", "Couldn't download data")
                }
            })
    }


    private suspend fun downloadContractors(contractors: List<Contractor>) {
        ContractorsDownloader.downloadContractors(contractors)
    }

    private suspend fun downloadNewOrders(orders: List<Order>) {
        AssemblyOrderDownloader.downloadDocuments(orders)
        AppNotificationManager.notifyUser()
    }

    fun sendAllOrdersTo1C() {

        GlobalScope.launch {
            val assemblyOrders = AssemblyOrderSender.getAllAssemblyOrdersForSending()
            assemblyOrders.forEach {
                sendOrderTo1C(it)
            }
        }
    }

    fun sendAllSimpleScanningTo1C() {

        GlobalScope.launch {
            val simpleScanning = SimpleScanningSender.getAllAssemblyOrdersForSending()
            simpleScanning.forEach {
                sendSimpleScanningTo1C(it)
            }
        }
    }

    suspend fun sendSimpleScanningTo1C(simpleScanning: SimpleScanning) {


        //ПОДГОТОВИМ ДАННЫЕ ДЛЯ ОТПРАВКИ
        val data = DataOutgoing(
            "scanning",
            DataOutgoing.OrderModel(
                simpleScanning.guid,
                simpleScanning.date,
                simpleScanning.id.toString(),
                simpleScanning.comment,
                SimpleScanningSender.getTableGoods(simpleScanning),
                null
            )
        )

        //ПОПРОБУЕМ ОТПРАВИТЬ ДАННЫЕ
        //Debug.waitForDebugger()
        Log.d("SimpleScanning_TO_1C", "Start sending")
        val retrofit = RetrofitClient()
        retrofit.initInstance()
        retrofit.instance.sendOrder(data)
            ?.enqueue(object : Callback<SendingStatus> {
                override fun onResponse(
                    call: Call<SendingStatus>,
                    response: Response<SendingStatus>
                ) {

                    //Debug.waitForDebugger()
                    Log.d("SimpleScanning_TO_1C", "Sending is completed")
                    //ПРИ УСПЕШНОЙ ВЫГРУЗКЕ ОБНОВИМ СТАТУС ОТПРАВЛЕННОСТИ ДОКУМЕНТА
                    if (response.code() == 200) {
                        Log.d("SimpleScanning_TO_1C", "Response 200")
                        GlobalScope.launch(Dispatchers.IO) {
                            SimpleScanningSender.makeOrderIsSent(simpleScanning)
                        }
                    }
                }

                override fun onFailure(call: Call<SendingStatus>, t: Throwable) {
                    Log.d("SimpleScanning_TO_1C", "Error")
                }
            })


    }


    suspend fun sendOrderTo1C(order: AssemblyOrder) {


        try {
            //ПОДГОТОВИМ ДАННЫЕ ДЛЯ ОТПРАВКИ
            val data = DataOutgoing(
                "orders",
                DataOutgoing.OrderModel(
                    order.guid,
                    order.date,
                    order.number,
                    order.comment,
                    AssemblyOrderSender.getAssemblyOrderTableGoods(order),
                    AssemblyOrderSender.getAssemblyOrderTableStamps(order)
                )
            )

            //ПОПРОБУЕМ ОТПРАВИТЬ ДАННЫЕ

            Log.d("AssemblyOrder_TO_1C", "Start sending")
            val retrofit = RetrofitClient()
            retrofit.initInstance()
            retrofit.instance.sendOrder(data)
                ?.enqueue(object : Callback<SendingStatus> {
                    override fun onResponse(
                        call: Call<SendingStatus>,
                        response: Response<SendingStatus>
                    ) {

                        //Debug.waitForDebugger()
                        Log.d("AssemblyOrder_TO_1C", "Sending is completed")
                        //ПРИ УСПЕШНОЙ ВЫГРУЗКЕ ОБНОВИМ СТАТУС ОТПРАВЛЕННОСТИ ДОКУМЕНТА
                        if (response.code() == 200) {
                            Log.d("AssemblyOrder_TO_1C", "Response 200")
                            GlobalScope.launch(Dispatchers.IO) {
                                AssemblyOrderSender.makeOrderIsSent(order)
                            }
                        }

                    }

                    override fun onFailure(call: Call<SendingStatus>, t: Throwable) {
                        Log.d("AssemblyOrder_TO_1C", "Error")
                    }
                })


        } catch (e: Exception) {

        } finally {

        }

    }
}

