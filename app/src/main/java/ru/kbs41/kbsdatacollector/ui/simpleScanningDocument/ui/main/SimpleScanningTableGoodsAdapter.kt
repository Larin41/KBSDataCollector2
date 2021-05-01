package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanningTableGoods
import java.lang.Exception


class SimpleScanningTableGoodsAdapter(
    private val list: LiveData<List<SimpleScanningTableGoods>>
) : RecyclerView.Adapter<SimpleScanningTableGoodsAdapter.ViewHolder>() {

    val database = App().database
    val productDao = database.productDao()
    val simpleScanningTableGoodsDao = database.simpleScanningTableGoodsDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_simple_scanning_table_goods_item, parent, false)
        return ViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = list.value?.get(position)
        val product = productDao.getProductById(note?.productId!!)
        val qty = list.value?.get(position)?.qty

        //Debug.waitForDebugger()
        holder.product.text = product?.name
        holder.qty.text = qty?.toString()
        holder.note = note

    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (list.value?.isEmpty() == false) {
            size = list.value!!.size
        }

        return size
    }

    inner class ViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var product: TextView = itemView.findViewById(R.id.tvProduct)
        var qty: TextView = itemView.findViewById(R.id.etQty)
        lateinit var note: SimpleScanningTableGoods
        private val menu: ImageButton = itemView.findViewById(R.id.stampsMenu)

        init {

            menu.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_delete -> {

                            val dao = App().database.assemblyOrderTableStampsDao()
                            GlobalScope.launch(Dispatchers.IO) {
                                simpleScanningTableGoodsDao.delete(
                                    note
                                )
                            }

                            true
                        }
                        else -> false
                    }
                }

                popupMenu.inflate(R.menu.stamps_menu)
                try {
                    val fieldMPopUp = PopupMenu::class.java.getDeclaredField("mPopup")
                    fieldMPopUp.isAccessible = true
                    val mPopup = fieldMPopUp.get(popupMenu)
                    mPopup.javaClass
                        .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                        .invoke(mPopup, true)
                } catch (e: Exception) {
                    Log.e("Stamps", "Error showing menu")
                }
                popupMenu.show()
            }


            val addBtn = itemView.findViewById<MaterialButton>(R.id.addQty)

            addBtn.setOnClickListener {

                var counterPlus: Double = 0.0
                var counterPlusText = qty.text.toString()
                if (counterPlusText != "") {
                    counterPlus = counterPlusText.toDouble()
                }

                counterPlus += 1.0

                qty.text = counterPlus.toString()
                note.qty = counterPlus
                GlobalScope.launch { simpleScanningTableGoodsDao.insert(note) }

            }

            val minusBtn = itemView.findViewById<MaterialButton>(R.id.minusQty)
            minusBtn.setOnClickListener {

                var counterMinus: Double = 0.0
                var counterMinusText = qty.text.toString()
                if (counterMinusText != "") {
                    counterMinus = counterMinusText.toDouble()
                }

                counterMinus -= 1.0
                if (counterMinus < 0.0) {
                    counterMinus = 0.0
                }

                qty.text = counterMinus.toString()
                note.qty = counterMinus
                GlobalScope.launch { simpleScanningTableGoodsDao.insert(note) }
            }

            itemView.setOnClickListener {
                /*
                 val intent = Intent(context, AssemblyOrderActivity::class.java)
                 intent.putExtra(
                     "AssemblyOrderId",
                     list.value!![adapterPosition].assemblyOrderTableStamps.id
                 )
                 ContextCompat.startActivity(context, intent, null)
             */
            }
        }
    }
}