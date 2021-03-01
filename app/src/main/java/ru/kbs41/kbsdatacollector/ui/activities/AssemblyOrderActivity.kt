package ru.kbs41.kbsdatacollector.ui.activities

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModelFactory
import ru.kbs41.kbsdatacollector.ui.MainViewModel
import ru.kbs41.kbsdatacollector.ui.MainViewModelFactory
import ru.kbs41.kbsdatacollector.ui.activities.ui.main.SectionsPagerAdapter

class AssemblyOrderActivity() : AppCompatActivity() {

    private val model: AssemblyOrderViewModel by viewModels {
        val assemblyOrderId = intent.extras?.getLong("AssemblyOrderId")
        AssemblyOrderViewModelFactory(assemblyOrderId!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        //ИНИЦИАЛИЗАЦИЯ
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly_order)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}