package ru.kbs41.kbsdatacollector.retrofit


import android.util.Base64
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kbs41.kbsdatacollector.App

object RetrofitClient {

    //TODO: ПОКА ЧТО ДЛЯ ПРИНЯТИЯ ИЗМЕНЕНИЙ НУЖНО ПЕРЕЗАГРУЗИТЬ ПРОГРАММУ

    private fun getBaseURL(): String {
        val settingsDao = App().database.settingsDao()
        val settings = settingsDao.getCurrentSettings()

        //АДРЕС СЕРВЕРА
        var baseUrl = if (settings.useHttps == true) {
            "https://"
        } else {
            "http://"
        }
        baseUrl += settings.server + ":" + settings.port

        return baseUrl
    }


    private fun getAuth(): String {

        val settingsDao = App().database.settingsDao()
        val settings = settingsDao.getCurrentSettings()

        //АВТОРИЗАЦИЯ
        val auth = "Basic " + Base64.encodeToString(
            "${settings.user}:${settings.password}".toByteArray(),
            Base64.NO_WRAP
        )

        return auth
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", getAuth())
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    val instance: Api by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        retrofit.create(Api::class.java)
    }

}