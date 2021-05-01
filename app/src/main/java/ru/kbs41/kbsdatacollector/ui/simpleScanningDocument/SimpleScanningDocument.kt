package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main.SectionsPagerAdapter

class SimpleScanningDocument : AppCompatActivity() {

    private val model: SimpleScanningViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.fetchData(intent.getLongExtra("simpleScanningId", 0L))


        setContentView(R.layout.activity_simple_scanning_document)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.simple_scnning_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}