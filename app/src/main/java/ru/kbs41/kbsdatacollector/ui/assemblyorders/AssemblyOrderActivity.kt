package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import ru.kbs41.kbsdatacollector.R
import kotlin.properties.Delegates

class AssemblyOrderActivity() : AppCompatActivity() {

    private val model: AssemblyOrderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        //ИНИЦИАЛИЗАЦИЯ
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly_order)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val assemblyOrderId = intent.extras?.getLong("AssemblyOrderId", 0L )
        model.initProperties(assemblyOrderId!!)
    }
}