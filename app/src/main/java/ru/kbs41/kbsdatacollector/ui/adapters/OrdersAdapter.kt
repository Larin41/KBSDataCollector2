package ru.kbs41.kbsdatacollector.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.withContext
import ru.kbs41.kbsdatacollector.CommonFunctions
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.activities.AssemblyOrderActivity
import kotlin.coroutines.coroutineContext


class OrdersAdapter(
    private val list: LiveData<List<AssemblyOrder>>
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.orders_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]
        val number = currentItem.number
        val date = CommonFunctions.getDateRussianFormat(currentItem.date)

        holder.contractor.text = currentItem.counterpart
        holder.dateNumber.text = "№$number от $date"
        holder.comment.text = currentItem.comment

    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    inner class OrdersViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var contractor: TextView = itemView.findViewById(R.id.contractor_name)
        var dateNumber: TextView = itemView.findViewById(R.id.date_number)
        var comment: TextView = itemView.findViewById(R.id.comment)


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra("AssemblyOrderId", list.value!![adapterPosition].id)
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }

}