package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.pojo.AssemblyOrderTableGoodsWithQtyCollectedAndProducts
import ru.kbs41.kbsdatacollector.ui.stamps.StampsReadingActivity


class AssemblyOrderTableGoodsAdapter(
    private val context: Context,
    private val model: AssemblyOrderViewModel,
    private val list: LiveData<List<AssemblyOrderTableGoodsWithQtyCollectedAndProducts>>
) : RecyclerView.Adapter<AssemblyOrderTableGoodsAdapter.OrdersViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_goods_item, parent, false)
        return OrdersViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = list.value!![position]

        if (currentItem.productHasStamps == true) {
            holder.cbCompleted.visibility = View.GONE
        } else {
            holder.cbCompleted.visibility = View.VISIBLE
        }

        holder.tableGoodsId = currentItem.id!!
        holder.number.text = currentItem.row.toString()
        holder.product.text = currentItem.productName
        holder.qty.text = FormatManager.getFormattedNumber(currentItem.qty)
        holder.qtyCollected.text = FormatManager.getFormattedNumber(currentItem.qtyCollected)
        if (currentItem.qty == currentItem.qtyCollected) {
            holder.qtyCollected.setTextColor(ContextCompat.getColor(context, R.color.teal_700))
            holder.cbCompleted.isChecked = true
        } else {
            holder.qtyCollected.setTextColor(ContextCompat.getColor(context, R.color.red))
            holder.cbCompleted.isChecked = false
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
        var tableGoodsId: Long = 0
        var number: TextView = itemView.findViewById(R.id.tvNumber)
        var product: TextView = itemView.findViewById(R.id.tvProduct)
        var qty: TextView = itemView.findViewById(R.id.tvQty)
        var qtyCollected: TextView = itemView.findViewById(R.id.tvQtyCollected)
        var cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)


        init {
            itemView.setOnClickListener {

                val currentPosition = list.value!![adapterPosition]

                if (currentPosition.productHasStamps != true) {
                    Toast.makeText(context, "Товар не нуждается в марках", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val intent = Intent(context, StampsReadingActivity::class.java)
                intent.putExtra("productId", currentPosition.productId)
                intent.putExtra("docId", currentPosition.orderID)
                intent.putExtra("qty", currentPosition.qty)
                intent.putExtra("row", currentPosition.id)

                ContextCompat.startActivity(context, intent, null)
            }

            cbCompleted.setOnClickListener {
                GlobalScope.launch {
                    model.setQtyCollectedTableGoods(
                        tableGoodsId,
                        !cbCompleted.isChecked
                    )
                }
            }
        }
    }
}