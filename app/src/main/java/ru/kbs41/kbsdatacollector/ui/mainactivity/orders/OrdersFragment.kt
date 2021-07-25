package ru.kbs41.kbsdatacollector.ui.mainactivity.orders

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.MainViewModel
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity


class OrdersFragment : Fragment() {

    companion object {
        fun newInstance() = OrdersFragment()
    }

    private val model: MainViewModel by activityViewModels()
    /*
    {
        MainViewModelFactory(((activity?.application) as App).assemblyOrdersRepository)
    }

     */


    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter
    private lateinit var fabAddNewAssemblyOrder: FloatingActionButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_orders, container, false)

        fabAddNewAssemblyOrder = rootView.findViewById(R.id.fabAddNewAssemblyOrder)
        fabAddNewAssemblyOrder.setOnClickListener{
            GlobalScope.launch {
                val id = model.addNewAssemblyOrder()
                val intent = Intent(context, AssemblyOrderActivity::class.java)
                intent.putExtra("AssemblyOrderId", id)
                ContextCompat.startActivity(requireContext(), intent, null)
            }
        }

        rwAdapterAllAssembly = AllAssemblyOrdersAdapter(model.allOrders)
        rwOrders = rootView.findViewById(R.id.rwOrders)
        rwOrders.layoutManager = LinearLayoutManager(context)
        rwOrders.adapter = rwAdapterAllAssembly

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Заказы"

        model.allOrders.observe(viewLifecycleOwner, { list ->
            list.let {
                rwAdapterAllAssembly.notifyDataSetChanged()
            }
        })

        return rootView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}