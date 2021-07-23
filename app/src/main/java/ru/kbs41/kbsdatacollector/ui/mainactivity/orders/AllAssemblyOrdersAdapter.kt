package ru.kbs41.kbsdatacollector.ui.mainactivity.orders

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.rawData.AssemblyOrdersWithContractors
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity
import java.lang.Exception


class AllAssemblyOrdersAdapter(
    private val list: LiveData<List<AssemblyOrdersWithContractors>>
) : RecyclerView.Adapter<AllAssemblyOrdersAdapter.OrdersViewHolder>() {

    val database = App().database
    val assemblyOrderDao = database.assemblyOrderDao()
    val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.orders_item, parent, false)

        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]
        val number = currentItem.number
        val date = FormatManager.getDateRussianFormat(currentItem.date)

        holder.id = currentItem.id
        holder.contractor.text = currentItem.contractor
        holder.dateNumber.text = "№$number от $date"
        holder.comment.text = currentItem.comment

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
        var id: Long = 0;
        var contractor: TextView = itemView.findViewById(R.id.contractor_name)
        var dateNumber: TextView = itemView.findViewById(R.id.date_number)
        var comment: TextView = itemView.findViewById(R.id.comment)

        private val menu: ImageButton = itemView.findViewById(R.id.menu)


        init {
            menu.setOnClickListener {
                val popupMenu = PopupMenu(context, it)
                popupMenu.setOnMenuItemClickListener { item ->
                    when (item.itemId) {
                        R.id.menu_item_delete -> {

                            GlobalScope.launch(Dispatchers.IO) {
                                assemblyOrderTableStampsDao.deleteByAssemblyOrderId(id)
                                assemblyOrderTableGoodsDao.deleteByAssemblyOrderId(id)
                                assemblyOrderDao.deleteByAssemblyOrderId(id)
                            }

                            true
                        }
                        else -> false
                    }
                }

                popupMenu.inflate(R.menu.delete_menu)
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
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra("AssemblyOrderId", list.value!![adapterPosition].id)
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }
}