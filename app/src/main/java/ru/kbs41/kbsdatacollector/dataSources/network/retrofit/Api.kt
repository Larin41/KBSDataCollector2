package ru.kbs41.kbsdatacollector.dataSources.network.retrofit

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataIncome
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.SendingStatus

interface Api {

    @GET("/torg/hs/KBS_TSD/getOrders")
    fun getData(@Header("deviceId") deviceId: Int,
                @Header("requiredData") requiredData: String): Call<DataIncome>

    @POST("/torg/hs/KBS_TSD/postOrder")
    fun sendOrder(@Body outgoingDataModel: DataOutgoing): Call<SendingStatus>


}