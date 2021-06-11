package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.Constants
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main.SectionsPagerAdapter

class SimpleScanningActivity : AppCompatActivity() {

    private val model: SimpleScanningViewModel by viewModels()

    private val receiverAtol = ReceiverAtol()
    private val receiverCaribe = ReceiverCaribe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.fetchData(intent.getLongExtra("simpleScanningId", 0L), this)


        setContentView(R.layout.activity_simple_scanning_document)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.simple_scnning_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        val btnSend = findViewById<ImageButton>(R.id.btnSendSimpleScanning)
        btnSend.setOnClickListener {
            model.sendSimpleScanning()
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiverAtol, IntentFilter(Constants.SCAN_ACTION_ATOL_SMART_LITE))
        registerReceiver(receiverCaribe, IntentFilter(Constants.SCAN_ACTION_CARIBE))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiverAtol)
        unregisterReceiver(receiverCaribe)
    }

    override fun onStop() {
        super.onStop()
    }

    inner class ReceiverAtol : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barcode = intent!!.getStringExtra(Constants.EXTRA_BARCODE_ATOL_SMART_LITE)

            GlobalScope.launch(Dispatchers.IO) { model.barcodeReading(barcode!!) }

        }

    }

    inner class ReceiverCaribe : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barocode = intent!!.getByteArrayExtra("barocode")
            val barocodelen = intent.getIntExtra("length", 0)

            val barcodeStr = String(barocode!!, 0, barocodelen)

            GlobalScope.launch(Dispatchers.IO) { model.barcodeReading(barcodeStr) }

        }

    }
}