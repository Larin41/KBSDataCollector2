package ru.kbs41.kbsdatacollector.retrofit

import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("/torg/hs/KBS_TSD/getOrders")
    fun getData() : Call<DataJSON>


}