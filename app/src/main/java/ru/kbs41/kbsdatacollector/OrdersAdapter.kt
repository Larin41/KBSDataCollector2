package ru.kbs41.kbsdatacollector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder


class OrdersAdapter : ListAdapter<AssemblyOrder, OrdersAdapter.OrdersViewHolder>(AssemblyOrdersComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        return OrdersViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class OrdersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val contractor: TextView = itemView.findViewById(R.id.tvContractorName)
        private val dateAndNumber: TextView = itemView.findViewById(R.id.tvDateNumber)

        fun bind(current: AssemblyOrder?) {
            val number = current?.number.toString()
            val date = CommonFunctions.getDateRussianFormat(current?.date)

            contractor.text = current?.counterpart
            dateAndNumber.text = "№$number от $date"
        }

        companion object {
            fun create(parent: ViewGroup): OrdersViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.orders_item, parent, false)
                return OrdersViewHolder(view)
            }
        }
    }

    class AssemblyOrdersComparator : DiffUtil.ItemCallback<AssemblyOrder>() {
        override fun areItemsTheSame(oldItem: AssemblyOrder, newItem: AssemblyOrder): Boolean {
            return oldItem.counterpart === newItem.counterpart
        }

        override fun areContentsTheSame(oldItem: AssemblyOrder, newItem: AssemblyOrder): Boolean {
            return oldItem.counterpart == newItem.counterpart
        }
    }
}