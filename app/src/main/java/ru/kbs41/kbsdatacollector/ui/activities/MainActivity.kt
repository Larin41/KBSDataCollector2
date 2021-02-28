package ru.kbs41.kbsdatacollector.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster
import ru.kbs41.kbsdatacollector.ui.MainViewModel
import ru.kbs41.kbsdatacollector.ui.fragments.OrdersFragment


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //TODO: удалить getData(). Нужно для отладки
        val ordersFragment = OrdersFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, ordersFragment)
            commit()
        }
    }

    private fun getData() {
        val em = ExchangeMaster()
        em.getData(application)
    }


}