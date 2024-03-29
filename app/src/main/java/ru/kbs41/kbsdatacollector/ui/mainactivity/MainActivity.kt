package ru.kbs41.kbsdatacollector.ui.mainactivity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.webkit.PermissionRequest
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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

    private val INTERNET_PERMISSION_CODE = 100
    private val RECEIVE_BOOT_COMPLETED_CODE = 101
   /* private val QUICKBOOT_POWERON_CODE = 102*/
    private val ACCESS_NETWORK_STATE_CODE = 103
    private val WAKE_LOCK_CODE = 104
    private val FOREGROUND_SERVICE_CODE = 105
    private val CHANGE_NETWORK_STATE_CODE = 106


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //ИНИЦИАЛИЗАЦИЯ ЭКЗЕМПЛЯРА БАЗЫ ДАННЫХ
        AppDatabase.getDatabase(application, null)
        try {

            //СТАРТ СЛУЖБЫ
            GlobalScope.launch {
                val service = Intent(applicationContext, ExchangerService::class.java)
                //delay(6000)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startService(service)
                } else {
                    ContextCompat.startForegroundService(this@MainActivity, service)
                }
            }

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