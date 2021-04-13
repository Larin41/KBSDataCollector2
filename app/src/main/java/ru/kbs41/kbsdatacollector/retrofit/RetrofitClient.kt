package ru.kbs41.kbsdatacollector.retrofit


import android.util.Base64
import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kbs41.kbsdatacollector.App
import java.io.IOException
import java.util.concurrent.TimeUnit

class RetrofitClient {

    //TODO: ПОКА ЧТО ДЛЯ ПРИНЯТИЯ ИЗМЕНЕНИЙ НУЖНО ПЕРЕЗАГРУЗИТЬ ПРОГРАММУ
    private val settingsDao = App().database.settingsDao()
    val settings = settingsDao.getCurrentSettings()


    private fun getBaseURL(): String {

        if (settings == null) {
            throw IOException("Не настроено подключение")
        }

        if (settings.server.isEmpty()
            || settings.port.isEmpty()
            || settings.deviceId == 0
            || settings.user.isEmpty()
            || settings.password.isEmpty()
        ) {
            throw IOException("Введены не все настройки подключения к серверу")
        }

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

        if (settings == null) {
            throw IOException("Не настроено подключение")
        }

        //АВТОРИЗАЦИЯ
        val auth = "Basic " + Base64.encodeToString(
            "${settings!!.user}:${settings!!.password}".toByteArray(),
            Base64.NO_WRAP
        )

        return auth
    }

    private val okHttpClient = OkHttpClient.Builder()
//        .readTimeout(90, TimeUnit.SECONDS)
//        .writeTimeout(90, TimeUnit.SECONDS)
//        .connectTimeout(90, TimeUnit.SECONDS)
        .addInterceptor { chain ->
            val original = chain.request()

            val requestBuilder = original.newBuilder()
                .addHeader("Authorization", getAuth())
                .method(original.method(), original.body())

            val request = requestBuilder.build()
            chain.proceed(request)
        }.build()

    private val gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    lateinit var instance: Api

    fun initInstance() {

        val retrofit = Retrofit.Builder()
            .baseUrl(getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()

        instance = retrofit.create(Api::class.java)

    }


}