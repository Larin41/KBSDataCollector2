package ru.kbs41.kbsdatacollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster


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
        em.getData(application)

    }


}