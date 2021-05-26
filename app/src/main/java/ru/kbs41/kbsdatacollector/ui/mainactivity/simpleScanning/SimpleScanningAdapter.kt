package ru.kbs41.kbsdatacollector.ui.mainactivity.simpleScanning

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
import ru.kbs41.kbsdatacollector.dataSources.dataBase.simpleScanning.SimpleScanning
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.SimpleScanningActivity


class SimpleScanningAdapter(
    private val list: LiveData<List<SimpleScanning>>
) : RecyclerView.Adapter<SimpleScanningAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_simple_scanning_item, parent, false)

        return ViewHolder(itemView, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = list.value!![position]
        val number = currentItem.id
        val date = FormatManager.getDateRussianFormat(currentItem.date)
        holder.dateNumber.text = "№$number от $date"
        holder.comment.text = currentItem.comment
    }

    override fun getItemCount(): Int {
        var size: Int = 0
        if (list.value != null) {
            size = list.value!!.size
        }

        return size
    }

    inner class ViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var dateNumber: TextView = itemView.findViewById(R.id.date_number)
        var comment: TextView = itemView.findViewById(R.id.comment)

        init {
            itemView.setOnClickListener {

                val intent = Intent(context, SimpleScanningActivity::class.java)
                intent.putExtra("simpleScanningId", list.value!![adapterPosition].id)
                ContextCompat.startActivity(context, intent, null)
            }
        }

    }

}