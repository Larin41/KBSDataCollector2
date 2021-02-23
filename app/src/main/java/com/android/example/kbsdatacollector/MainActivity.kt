package com.android.example.kbsdatacollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.android.example.kbsdatacollector.retrofit.Api
import com.android.example.kbsdatacollector.retrofit.ExchangeMaster
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener(View.OnClickListener {
            getData()
        })


    }

    private fun getData() {

        val em = ExchangeMaster()
        em.getData()

    }


}