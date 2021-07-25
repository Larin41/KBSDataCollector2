package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.Constants
import ru.kbs41.kbsdatacollector.R

class AssemblyOrderActivity() : AppCompatActivity() {

    private val model: AssemblyOrderViewModel by viewModels()

    private val receiverAtol = ReceiverAtol()
    private val receiverCaribe = ReceiverCaribe()

    override fun onCreate(savedInstanceState: Bundle?) {
        //ИНИЦИАЛИЗАЦИЯ
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assembly_order)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.simple_scnning_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        val assemblyOrderId = intent.extras?.getLong("AssemblyOrderId", 0L)
        model.fetchData(this, assemblyOrderId!!)

        val actionBar = supportActionBar
        actionBar?.title = "New Title"

        val acceptButton: ImageButton = findViewById(R.id.acceptAssemblyOrderButton)
        acceptButton.setOnClickListener {
            val mayClose = model.completeOrder()
            if (mayClose) {
                finish()
            }

        }

        val editButton: ImageButton = findViewById(R.id.editAssemblyOrderButton)
        editButton.setOnClickListener {

            model.editGoods.value = model.editGoods.value != true

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