package ru.kbs41.kbsdatacollector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder


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