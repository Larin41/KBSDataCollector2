package com.android.example.kbsdatacollector.retrofit



import android.util.Log
import com.android.example.kbsdatacollector.room.AppDatabase
import com.android.example.kbsdatacollector.room.db.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class ExchangeMaster {

    fun getBaseURL(): String {

        return "http://192.168.1.50"
    }


    fun getData() {

        val baseUrl = getBaseURL()

        val api = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getData().awaitResponse()
            if (response.isSuccessful) {
                val data : DataJSON = response.body()!!

                for (i in data.goods){

                    Log.d("GOODS", i.name.toString())

                    val productDao = AppDatabase.getDatabase(this, CoroutineScope(Dispatchers.IO)).productDao()

                    var foundProducts = productDao.getProductByGuid(i.guid)

                    var product: Product = Product(
                       0,
                        i.name,
                        i.unit,
                        i.isAlcohol,
                        i.hasStamp,
                        i.guid,
                        i.isFolder,
                        i.parentGuid,
                        foundProducts[0].id
                    )




                }


            }
        }

    }


}