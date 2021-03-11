package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kbs41.kbsdatacollector.R

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
        model.fetchData(this, assemblyOrderId!!)

        val fb: FloatingActionButton = findViewById(R.id.fbAcceptAssemblyOrder)
        fb.setOnClickListener{
            model.completeOrder()
        }

    }
}