package ru.kbs41.kbsdatacollector.dataSources.network


import android.app.Application
import android.os.Debug
import android.util.Log
import kotlinx.coroutines.*
import retrofit2.*
import ru.kbs41.kbsdatacollector.notificationManager.AppNotificationManager
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.IncomeDataOrders
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.SendingStatus
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.network.downloaders.AssemblyOrderDownloader
import ru.kbs41.kbsdatacollector.dataSources.network.downloaders.GoodsDownloader
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.RetrofitClient
import ru.kbs41.kbsdatacollector.dataSources.network.senders.AssemblyOrderSender
import java.io.IOException

object ExchangeMaster {

    fun getAllGoodsFrom1C() {

        //Debug.waitForDebugger()

        val settings = mapOf(
            "deviceId" to "1",
            "requiredData" to "allGoods"
        )

        try {
            getData(settings)
        } catch (e: IOException) {
            Log.d("1C_TO_APP", e.message!!)
        } finally {
            //DO NOTHING
        }

    }

    fun getOrdersFrom1C(application: Application) {

        val settings = mapOf(
            "deviceId" to "1",
            "requiredData" to "newOrders"
        )

        try {
            getData(settings)
        } catch (e: IOException) {
            Log.d("1C_TO_APP", e.message!!)
        } finally {
            //DO NOTHING
        }
    }

    private fun getData(settings: Map<String, String>) {

        Debug.waitForDebugger()
        val retrofit = RetrofitClient()

        retrofit.initInstance()

        if (settings["requiredData"]!! == "newOrders") {
            getNewOrders(retrofit, settings)
        }

        if (settings["requiredData"]!! == "allGoods") {
            getAllGoods(retrofit, settings)
        }


    }

    private fun getAllGoods(retrofit: RetrofitClient, settings: Map<String, String>) {
        retrofit.instance.getOrders(settings["deviceId"]!!, settings["requiredData"]!!)
            ?.enqueue(object : Callback<IncomeDataOrders> {
                override fun onResponse(
                    call: Call<IncomeDataOrders>,
                    response: Response<IncomeDataOrders>
                ) {
                    GlobalScope.launch(Dispatchers.IO) {

                        Debug.waitForDebugger()
                        val body: IncomeDataOrders? = response.body()
                        if (body == null) {
                            Log.d("1C_TO_APP", "Null body")
                            return@launch
                        }

                        Log.d("1C_TO_APP", body.result)

                        if (body.result != "Ok") {
                            return@launch
                        }

                        if (response.isSuccessful) {
                            downloadGoods(body)
                        }
                    }
                }

                override fun onFailure(call: Call<IncomeDataOrders>, t: Throwable) {
                    Log.d("1C_TO_APP", "Couldn't download data")
                }
            })
    }

    private suspend fun downloadGoods(body: IncomeDataOrders) {
        Debug.waitForDebugger()
        GoodsDownloader().downloadCatalogs(body.goods)
    }

    private fun getNewOrders(retrofit: RetrofitClient, settings: Map<String, String>) {
        retrofit.instance.getOrders(settings["deviceId"]!!, settings["requiredData"]!!)
            ?.enqueue(object : Callback<IncomeDataOrders> {
                override fun onResponse(
                    call: Call<IncomeDataOrders>,
                    response: Response<IncomeDataOrders>
                ) {
                    GlobalScope.launch(Dispatchers.IO) {

                        val body: IncomeDataOrders? = response.body()
                        if (body == null) {
                            Log.d("1C_TO_APP", "Null body")
                            return@launch
                        }

                        Log.d("1C_TO_APP", body.result)

                        if (body.result != "Ok") {
                            return@launch
                        }

                        if (response.isSuccessful) {
                            downloadNewOrders(body)
                        }
                    }
                }

                override fun onFailure(call: Call<IncomeDataOrders>, t: Throwable) {
                    Log.d("1C_TO_APP", "Couldn't download data")
                }
            })
    }


    private suspend fun downloadNewOrders(body: IncomeDataOrders) {
        val goodsDownloader = GoodsDownloader()
        goodsDownloader.downloadCatalogs(body.goods)

        val assemblyOrderDownloader = AssemblyOrderDownloader()
        assemblyOrderDownloader.downloadDocuments(body.orders)

        if (body.orders != null) {
            AppNotificationManager.notifyUser()
        }
    }

    fun sendAllOrdersTo1C() {

        GlobalScope.launch {
            val assemblyOrders = AssemblyOrderSender.getAllAssemblyOrdersForSending()
            assemblyOrders.forEach {
                sendOrderTo1C(it)
            }
        }
    }

    suspend fun sendOrderTo1C(order: AssemblyOrder) {


        //ПОДГОТОВИМ ДАННЫЕ ДЛЯ ОТПРАВКИ
        val data = DataOutgoing(
            DataOutgoing.OrderModel(
                order.guid,
                order.date,
                order.number,
                AssemblyOrderSender.getAssemblyOrderTableGoods(order),
                AssemblyOrderSender.getAssemblyOrderTableStamps(order)
            )
        )

        //ПОПРОБУЕМ ОТПРАВИТЬ ДАННЫЕ

        Log.d("APP_TO_1C", "Start sending")
        val retrofit = RetrofitClient()
        retrofit.initInstance()
        retrofit.instance.sendOrder(data)
            ?.enqueue(object : Callback<SendingStatus> {
                override fun onResponse(
                    call: Call<SendingStatus>,
                    response: Response<SendingStatus>
                ) {

                    //Debug.waitForDebugger()
                    Log.d("APP_TO_1C", "Sending is completed")
                    //ПРИ УСПЕШНОЙ ВЫГРУЗКЕ ОБНОВИМ СТАТУС ОТПРАВЛЕННОСТИ ДОКУМЕНТА
                    if (response.code() == 200) {
                        Log.d("APP_TO_1C", "Response 200")
                        GlobalScope.launch(Dispatchers.IO) {
                            AssemblyOrderSender.makeOrderIsSent(
                                order
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<SendingStatus>, t: Throwable) {
                    Log.d("APP_TO_1C", "HUEVO")
                }
            })


    }
}

