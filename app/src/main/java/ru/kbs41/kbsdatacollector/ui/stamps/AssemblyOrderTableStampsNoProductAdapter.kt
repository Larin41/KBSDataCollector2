package ru.kbs41.kbsdatacollector.ui.stamps

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableStampsWithProducts
import java.lang.Exception


class AssemblyOrderTableStampsNoProductAdapter(
    private val list: LiveData<List<AssemblyOrderTableStampsWithProducts>>
) : RecyclerView.Adapter<AssemblyOrderTableStampsNoProductAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_stamps_item_no_product, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position].assemblyOrderTableStamps
        holder.id = currentItem.id
        holder.number.text = (position + 1).toString()
        holder.stamp.text = currentItem.barcode

    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (list.value != null) {
            size = list.value!!.size
        }
        return size
    }

    inner class OrdersViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var id: Long? = null
        var stamp: TextView = itemView.findViewById(R.id.tvStamp)
        var number: TextView = itemView.findViewById(R.id.tvNumber)
        private val menu: ImageButton = itemView.findViewById(R.id.stampsMenu)


        init {
            menu.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_delete -> {

                            val dao = App().database.assemblyOrderTableStampsDao()
                            GlobalScope.launch(Dispatchers.IO) { dao.deleteById(id!!) }

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