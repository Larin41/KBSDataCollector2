package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.Constants
import ru.kbs41.kbsdatacollector.R
import androidx.lifecycle.ViewModelProvider

class StampsReadingActivity : AppCompatActivity() {

    private val receiverAtol = ReceiverAtol()
    private val receiverCaribe = ReceiverCaribe()

    lateinit var viewModel: StampsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamps_reading)

        viewModel = ViewModelProvider(this).get(StampsViewModel::class.java)

        val sectionsPagerAdapter = StampsSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.stamps_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.stamps_tabs)
        tabs.setupWithViewPager(viewPager)

        val docId = intent.getLongExtra("docId", 0)
        val productId = intent.getLongExtra("productId", 0)
        val qty = intent.getDoubleExtra("qty", 0.0)
        val row = intent.getLongExtra("row", 0)

        viewModel.initProperties(row, docId, productId, qty, this)

    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiverAtol)
        unregisterReceiver(receiverCaribe)
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiverAtol, IntentFilter(Constants.SCAN_ACTION_ATOL_SMART_LITE))
        registerReceiver(receiverCaribe, IntentFilter(Constants.SCAN_ACTION_CARIBE))
    }

    inner class ReceiverAtol : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barcode = intent!!.getStringExtra(Constants.EXTRA_BARCODE_ATOL_SMART_LITE)

            GlobalScope.launch(Dispatchers.IO) { viewModel.insertNewStamp(barcode!!) }

        }

    }

    inner class ReceiverCaribe : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barocode = intent!!.getByteArrayExtra("barocode")
            val barocodelen = intent.getIntExtra("length", 0)

            val barcodeStr = String(barocode!!, 0, barocodelen)

            GlobalScope.launch(Dispatchers.IO) { viewModel.insertNewStamp(barcodeStr) }

        }

    }
}
