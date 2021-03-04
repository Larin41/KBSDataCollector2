package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModelFactory
import ru.kbs41.kbsdatacollector.ui.assemblyorders.SectionsPagerAdapter

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