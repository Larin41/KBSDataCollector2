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
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.MainViewModel
import ru.kbs41.kbsdatacollector.ui.MainViewModelFactory

class OrderGoodsFragment : Fragment() {

    companion object {
        fun newInstance() = OrderGoodsFragment()
    }

    private val model: MainViewModel by activityViewModels{
        MainViewModelFactory(((activity?.application) as App).assemblyOrdersRepository)
    }

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



        model.test.observe(viewLifecycleOwner, Observer<String> { string ->
            btn.text = string
        })



        model.allOrders.observe(viewLifecycleOwner, Observer<List<AssemblyOrder>> { list ->
            list.let {
                rwAdapterAllAssembly.notifyDataSetChanged() }
        })


        btn.setOnClickListener{
            model.test.value = model.test.value + "1"
            activity?.let { ExchangeMaster().getData(it.application) }
        }

        /*
        model.test.observe(viewLifecycleOwner, Observer<List<AssemblyOrder>> { orders ->
            //Toast.makeText(context, orders.size.toString(), Toast.LENGTH_SHORT).show()
        })
         */

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}