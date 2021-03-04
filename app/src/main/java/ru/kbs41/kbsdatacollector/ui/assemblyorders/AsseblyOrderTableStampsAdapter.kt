package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStampsWithProducts


class AsseblyOrderTableStampsAdapter(
    private val list: LiveData<List<AssemblyOrderTableStampsWithProducts>>
) : RecyclerView.Adapter<AsseblyOrderTableStampsAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_goods_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position].assemblyOrderTableStamps
        val currentProduct = list.value!![position].product

        holder.product.text = currentProduct.name
        holder.stamp.text = currentItem.barcode.toString()

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
        var product: TextView = itemView.findViewById(R.id.tvProduct)
        var stamp: TextView = itemView.findViewById(R.id.tvStamp)


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra(
                    "AssemblyOrderId",
                    list.value!![adapterPosition].assemblyOrderTableStamps.id
                )
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }

}