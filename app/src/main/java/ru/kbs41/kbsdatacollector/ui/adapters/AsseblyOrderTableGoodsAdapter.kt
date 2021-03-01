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
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.ui.activities.AssemblyOrderActivity
import kotlin.coroutines.coroutineContext


class AsseblyOrderTableGoodsAdapter(
    private val list: LiveData<List<AssemblyOrderTableGoods>>
) : RecyclerView.Adapter<AsseblyOrderTableGoodsAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_goods_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]


        holder.number.text = currentItem.row.toString()
        holder.product.text = currentItem.ProductId.toString()
        holder.qty.text = currentItem.qty.toString()
        holder.qtyCollected.text = currentItem.qtyCollected.toString()

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
        var number: TextView = itemView.findViewById(R.id.tvNumber)
        var product: TextView = itemView.findViewById(R.id.tvProduct)
        var qty: TextView = itemView.findViewById(R.id.tvQty)
        var qtyCollected: TextView = itemView.findViewById(R.id.tvQtyCollected)


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra("AssemblyOrderId", list.value!![adapterPosition].id)
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }

}