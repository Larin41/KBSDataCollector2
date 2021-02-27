package ru.kbs41.kbsdatacollector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import javax.sql.DataSource
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.coroutineContext


class OrdersAdapter(
    private val list: LiveData<List<AssemblyOrder>>
) : RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.orders_item, parent, false)
        return OrdersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]

        val number = currentItem.number.toString()
        val date = CommonFunctions.getDateRussianFormat(currentItem.date)

        holder.contractor.text = currentItem.counterpart.toString()
        holder.dateNumber.text = "№$number от $date"
    }

    override fun getItemCount(): Int {
        return list.value!!.size
    }

    inner class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var contractor: TextView = itemView.findViewById(R.id.contractor_name)
        var dateNumber: TextView = itemView.findViewById(R.id.date_number)


        init {
            itemView.setOnClickListener { list.value!![adapterPosition].id }
        }

    }

}