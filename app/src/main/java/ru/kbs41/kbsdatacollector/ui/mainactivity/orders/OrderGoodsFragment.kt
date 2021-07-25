package ru.kbs41.kbsdatacollector.ui.mainactivity.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.MainViewModel
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity

class OrderGoodsFragment : Fragment() {

    companion object {
        fun newInstance() = OrderGoodsFragment()
    }

    private val model: MainViewModel by activityViewModels()

    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_orders, container, false)


        rwAdapterAllAssembly = AllAssemblyOrdersAdapter(model.allOrders)
        rwOrders = rootView.findViewById<RecyclerView>(R.id.rwOrders)
        rwOrders.layoutManager = LinearLayoutManager(context)
        rwOrders.adapter = rwAdapterAllAssembly


        model.allOrders.observe(viewLifecycleOwner, { list ->
            list.let {
                rwAdapterAllAssembly.notifyDataSetChanged() }
        })


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}