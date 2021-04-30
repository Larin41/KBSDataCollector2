package ru.kbs41.kbsdatacollector.ui.mainactivity.goods

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.dataBase.products.Product
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity


class GoodsAdapter(
    private val list: LiveData<List<Product>>
) : RecyclerView.Adapter<GoodsAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_product_item, parent, false)

        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]
        holder.productName.text = currentItem.name
            }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (list.value != null){
            size = list.value!!.size
        }

        return size
    }

    inner class OrdersViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var productName: TextView = itemView.findViewById(R.id.tvProductName)

        init {
            itemView.setOnClickListener {
                /*
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra("AssemblyOrderId", list.value!![adapterPosition].id)
                ContextCompat.startActivity(context, intent, null)
                 */
            }
        }

    }

}