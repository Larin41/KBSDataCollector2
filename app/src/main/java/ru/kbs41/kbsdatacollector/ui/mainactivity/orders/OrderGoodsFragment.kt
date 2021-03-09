package ru.kbs41.kbsdatacollector.ui.mainactivity.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.MainViewModel

class OrderGoodsFragment : Fragment() {

    companion object {
        fun newInstance() = OrderGoodsFragment()
    }

    private val model: MainViewModel by activityViewModels()

    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter
    private lateinit var btn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_orders, container, false)

        rwAdapterAllAssembly = AllAssemblyOrdersAdapter(model.allOrders)
        rwOrders = rootView.findViewById<RecyclerView>(R.id.rwOrders)
        rwOrders.layoutManager = LinearLayoutManager(context)
        rwOrders.adapter = rwAdapterAllAssembly


        model.allOrders.observe(viewLifecycleOwner, Observer<List<AssemblyOrder>> { list ->
            list.let {
                rwAdapterAllAssembly.notifyDataSetChanged() }
        })


        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}