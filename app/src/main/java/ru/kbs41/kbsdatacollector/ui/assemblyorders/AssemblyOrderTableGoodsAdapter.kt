package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
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
import ru.kbs41.kbsdatacollector.room.db.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.ui.stamps.StampsReadingActivity


class AssemblyOrderTableGoodsAdapter(
    private val context: Context,
    private val list: LiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>
) : RecyclerView.Adapter<AssemblyOrderTableGoodsAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_goods_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]

        holder.number.text = currentItem.row.toString()
        holder.product.text = currentItem.productName
        holder.qty.text = CommonFunctions.getFormattedNumber(currentItem.qty)
        holder.qtyCollected.text = CommonFunctions.getFormattedNumber(currentItem.qtyCollected)
        if (currentItem.qty == currentItem.qtyCollected){
            holder.qtyCollected.setTextColor(ContextCompat.getColor(context, R.color.teal_700))
        } else {
            holder.qtyCollected.setTextColor(ContextCompat.getColor(context, R.color.red))
        }


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
                val intent = Intent(context, StampsReadingActivity::class.java)
                intent.putExtra("productId", list.value!![adapterPosition].productId)
                intent.putExtra("docId", list.value!![adapterPosition].orderID)
                intent.putExtra("qty", list.value!![adapterPosition].qty)

                ContextCompat.startActivity(context, intent, null)
            }
        }
    }
}