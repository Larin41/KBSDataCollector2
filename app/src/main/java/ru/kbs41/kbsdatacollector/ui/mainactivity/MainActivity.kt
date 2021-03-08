package ru.kbs41.kbsdatacollector.ui.mainactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster


class MainActivity : AppCompatActivity() {

    private lateinit var navView: NavigationView
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navView = findViewById(R.id.navView)
        drawerLayout = findViewById(R.id.drawerLayout)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_opened, R.string.nav_closed)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        //TODO: удалить getData(). Нужно для отладки
        getData()
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


}