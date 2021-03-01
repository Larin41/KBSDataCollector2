package ru.kbs41.kbsdatacollector.ui.activities.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.CommonFunctions
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderBinding
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModelFactory
import ru.kbs41.kbsdatacollector.ui.adapters.OrdersAdapter


/**
 * A placeholder fragment containing a simple view.
 */
class AssemblyOrderFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderBinding? = null
    private val binding get() = _binding!!


    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapter: OrdersAdapter

    private val model: AssemblyOrderViewModel by activityViewModels() {
        AssemblyOrderViewModelFactory(requireActivity().intent.getLongExtra("AssemblyOrderId", 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssemblyOrderBinding.inflate(inflater, container, false)
        val root = binding.root

        val order = model.assemblyOrders.value?.get(0)
        if (order != null) {
            binding.date = CommonFunctions.getDateRussianFormat(order.date)
            binding.number = order.number
            binding.contractor = order.counterpart
            binding.comment = order.comment
        }


        model.assemblyOrders.observe(viewLifecycleOwner, Observer<List<AssemblyOrder>> { list ->
            list.let {
                val order = it[0]
                binding.date = CommonFunctions.getDateRussianFormat(order.date)
                binding.number = order.number.toString()
                binding.contractor = order.counterpart
                binding.comment = order.comment
            }
        })

        Toast.makeText(context, "" + model.assemblyOrders.value.toString(), Toast.LENGTH_SHORT)
            .show()
        return root

    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): AssemblyOrderFragment {
            return AssemblyOrderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}