package ru.kbs41.kbsdatacollector.dataSources.network.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.kbs41.kbsdatacollector.dataSources.network.models.IncomeDataModel
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.IncomeGoodsModel
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.SendingStatus

interface Api {

    @GET("/torg/hs/KBS_TSD/getData")
    fun getOrders(@Header("deviceId") deviceId: String,
                  @Header("requiredData") requiredData: String): Call<IncomeDataModel>

    @GET("/torg/hs/KBS_TSD/getData")
    fun getGoods(@Header("deviceId") deviceId: String,
                  @Header("requiredData") requiredData: String): Call<IncomeGoodsModel>

    @POST("/torg/hs/KBS_TSD/postData")
    fun sendOrder(@Body outgoingDataModel: DataOutgoing): Call<SendingStatus>


}