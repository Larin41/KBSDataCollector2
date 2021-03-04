package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import ru.kbs41.kbsdatacollector.R

class StampsReading : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamps_reading)
        val sectionsPagerAdapter = StampsSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.stamps_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.stamps_tabs)
        tabs.setupWithViewPager(viewPager)

    }
}