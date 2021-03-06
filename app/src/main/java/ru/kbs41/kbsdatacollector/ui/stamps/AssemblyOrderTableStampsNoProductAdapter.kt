package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStampsWithProducts


class AssemblyOrderTableStampsNoProductAdapter(
    private val list: LiveData<List<AssemblyOrderTableStampsWithProducts>>
) : RecyclerView.Adapter<AssemblyOrderTableStampsNoProductAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_stamps_item_no_product, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position].assemblyOrderTableStamps
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
        var stamp: TextView = itemView.findViewById(R.id.tvStamp)
        var number: TextView = itemView.findViewById(R.id.tvNumber)


        init {
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