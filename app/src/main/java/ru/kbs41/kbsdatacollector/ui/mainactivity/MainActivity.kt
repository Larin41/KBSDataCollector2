package ru.kbs41.kbsdatacollector.ui.mainactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.kbs41.kbsdatacollector.ExchangerService
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.AppDatabase
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster
import ru.kbs41.kbsdatacollector.ui.mainactivity.goods.GoodsFragment
import ru.kbs41.kbsdatacollector.ui.mainactivity.orders.OrdersFragment
import ru.kbs41.kbsdatacollector.ui.mainactivity.settings.SettingsFragment
import ru.kbs41.kbsdatacollector.ui.mainactivity.simpleScanning.SimpleScanningFragment
import java.io.IOException


class MainActivity : AppCompatActivity() {

    private val ordersFragment = OrdersFragment()
    private val simpleScanningFragment = SimpleScanningFragment()
    private val goodsFragment = GoodsFragment()
    private val settingsFragment = SettingsFragment()

    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ИНИЦИАЛИЗАЦИЯ ЭКЗЕМПЛЯРА БАЗЫ ДАННЫХ
        AppDatabase.getDatabase(application, null)
        try {

            //СТАРТ СЛУЖБЫ
            val service = Intent(this, ExchangerService::class.java)
            //startService(service)
            ContextCompat.startForegroundService(this, service)

            //TODO: удалить getData(). Нужно для отладки
            getData()
        } catch (e: IOException) {
            Log.d("1C_TO_APP", e.message!!)
        }


        supportFragmentManager.beginTransaction().apply {
            replace(R.id.container, ordersFragment)
            commit()


            navView = findViewById(R.id.navView)
            drawerLayout = findViewById(R.id.drawerLayout)

            toggle = ActionBarDrawerToggle(
                this@MainActivity,
                drawerLayout,
                R.string.nav_opened,
                R.string.nav_closed
            )
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            val actionBar = supportActionBar
            actionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.nav_orders -> supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, ordersFragment)
                            .addToBackStack(OrdersFragment::class.java.name)
                            .commit()
                    }

                    R.id.nav_simple_scanning -> supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, simpleScanningFragment)
                            .addToBackStack(OrdersFragment::class.java.name)
                            .commit()
                    }

                    R.id.nav_goods -> supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, goodsFragment)
                            .addToBackStack(OrdersFragment::class.java.name)
                            .commit()
                    }

                    R.id.nav_settings -> supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container, settingsFragment)
                            .addToBackStack(SettingsFragment::class.java.name)
                            .commit()
                    }

                }
                drawerLayout.closeDrawer(GravityCompat.START)
                true
            }


        }
    }

    private fun getData() {
        ExchangeMaster.getOrdersFrom1C(application)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }

    }

}