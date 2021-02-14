package com.android.example.kbsdatacollector

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.example.kbsdatacollector.room.db.Product


class ProductListAdapter : ListAdapter<Product, ProductListAdapter.ProductViewHolder>(PRODUCTS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.name)
    }

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val productItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            productItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ProductViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ProductViewHolder(view)
            }
        }
    }

    companion object {
        private val PRODUCTS_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}