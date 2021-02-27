package ru.kbs41.kbsdatacollector

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import androidx.room.FtsOptions
import ru.kbs41.kbsdatacollector.retrofit.ExchangeMaster
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder

class OrdersFragment : Fragment() {

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private val model:MainViewModel by activityViewModels{
        MainViewModelFactory(((activity?.application) as App).assemblyOrdersRepository)
    }

    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapter: OrdersAdapter
    private lateinit var btn: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.orders_fragment, container, false)

        rwAdapter = OrdersAdapter()
        btn = rootView.findViewById<Button>(R.id.btnSexy)
        rwOrders = rootView.findViewById<RecyclerView>(R.id.rwOrders)
        rwOrders.layoutManager = LinearLayoutManager(context)
        rwOrders.adapter = rwAdapter


        model.test.observe(viewLifecycleOwner, Observer<String> { string ->
            btn.text = model.test.value
        })


        model.allOrders.observe(viewLifecycleOwner, Observer<List<AssemblyOrder>> { list ->
            list.let { rwAdapter.submitList(list) }
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