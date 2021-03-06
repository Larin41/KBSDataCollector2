package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStampsWithProducts


class AssemblyOrderTableStampsAdapter(
    private val list: LiveData<List<AssemblyOrderTableStampsWithProducts>>
) : RecyclerView.Adapter<AssemblyOrderTableStampsAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_stamps_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value?.get(position)?.assemblyOrderTableStamps
        val currentProduct = list.value?.get(position)?.product

        holder.product.text = currentProduct?.name
        holder.stamp.text = currentItem?.barcode

    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (list.value?.isEmpty() == false) {
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