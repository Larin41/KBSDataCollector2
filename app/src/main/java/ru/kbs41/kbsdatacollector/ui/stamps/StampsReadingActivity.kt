package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Debug
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kbs41.kbsdatacollector.Constants
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.soundManager.SoundEffects

class StampsReadingActivity : AppCompatActivity() {

    private val receiverAtol = ReceiverAtol()
    private val receiverCaribe = ReceiverCaribe()

    private val viewModel: StampsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stamps_reading)

        val sectionsPagerAdapter = StampsSectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.stamps_view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.stamps_tabs)
        tabs.setupWithViewPager(viewPager)

        val orderId = intent.getLongExtra("docId", 0)
        val productId = intent.getLongExtra("productId", 0)
        val tableGoodsRowId = intent.getLongExtra("tableGoodsId", 0)
        val addedManually = intent.getBooleanExtra("addedManually", false)
        viewModel.fetchData(productId, tableGoodsRowId, orderId, addedManually)


        //УСТАРЕВШИЕ

        val row = intent.getLongExtra("row", 0)


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

            GlobalScope.launch(Dispatchers.IO) {
                val errorsDescription = viewModel.insertNewStamp(barcode!!)
                noticeAboutErrors(errorsDescription)
            }

        }

    }

    inner class ReceiverCaribe : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            val barocode = intent!!.getByteArrayExtra("barocode")
            val barocodelen = intent.getIntExtra("length", 0)

            val barcodeStr = String(barocode!!, 0, barocodelen)

            GlobalScope.launch(Dispatchers.IO) {
                val errorsDescription = viewModel.insertNewStamp(barcodeStr)
                noticeAboutErrors(errorsDescription)
            }


        }

    }

    private suspend fun noticeAboutErrors(errorsDescription: ErrorsDescription) {

        withContext(Dispatchers.Main) {

            //Debug.waitForDebugger()

            if (errorsDescription.scanningComplete) {
                SoundEffects().playSuccess(applicationContext)
                return@withContext
            }

            if (errorsDescription.hasProblems) {
                SoundEffects().playError(applicationContext)
            }

            if (errorsDescription.stampsAreCollected) {
                Toast.makeText(applicationContext, "Марки уже подобраны", Toast.LENGTH_SHORT)
                    .show()
            }
            if (errorsDescription.problemsWithBarcodeFormat) {
                Toast.makeText(applicationContext, "Неверный формат штрихкода", Toast.LENGTH_SHORT)
                    .show()
            }
            if (errorsDescription.problemsWithExistedStamp) {
                Toast.makeText(applicationContext, "Марка уже была считана", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}
