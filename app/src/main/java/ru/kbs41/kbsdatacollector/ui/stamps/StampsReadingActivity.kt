package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.Constants
import ru.kbs41.kbsdatacollector.R

class StampsReadingActivity : AppCompatActivity() {

    private val receiverAtol = ReceiverAtol()
    private val receiverCaribe = ReceiverCaribe()

    private val model: StampsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamps_reading)
        val sectionsPagerAdapter = StampsSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.stamps_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.stamps_tabs)
        tabs.setupWithViewPager(viewPager)

        val docId = intent.getLongExtra("docId", 0)
        val productId = intent.getLongExtra("productId", 0)

        model.initProperties(docId, productId)

    }

    override fun onStart() {
        super.onStart()

        registerReceiver(receiverAtol, IntentFilter(Constants.SCAN_ACTION_ATOL_SMART_LITE))
        registerReceiver(receiverCaribe, IntentFilter(Constants.SCAN_ACTION_CARIBE))
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(receiverAtol)
        unregisterReceiver(receiverCaribe)

    }

    inner class ReceiverAtol : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barcode = intent!!.getStringExtra(Constants.EXTRA_BARCODE_ATOL_SMART_LITE)

            GlobalScope.launch(Dispatchers.IO) { model.insertNewStamp(barcode!!) }

        }

    }

    inner class ReceiverCaribe : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barocode = intent!!.getByteArrayExtra("barocode")
            val barocodelen = intent.getIntExtra("length", 0)

            val barcodeStr = String(barocode!!, 0, barocodelen)

            GlobalScope.launch(Dispatchers.IO) { model.insertNewStamp(barcodeStr) }

        }

    }
}
