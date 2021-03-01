package ru.kbs41.kbsdatacollector.ui.activities.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderTableGoodsBinding
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableGoods
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModel
import ru.kbs41.kbsdatacollector.ui.AssemblyOrderViewModelFactory
import ru.kbs41.kbsdatacollector.ui.adapters.AsseblyOrderTableGoodsAdapter
import ru.kbs41.kbsdatacollector.ui.adapters.OrdersAdapter


/**
 * A placeholder fragment containing a simple view.
 */
class AssemblyOrderTableGoodsFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderTableGoodsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AsseblyOrderTableGoodsAdapter
    private lateinit var rwTableGoods: RecyclerView

    val model: AssemblyOrderViewModel by activityViewModels() {
        AssemblyOrderViewModelFactory(requireActivity().intent.getLongExtra("AssemblyOrderId", 0))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toast.makeText(context, "" + model.assemblyOrders.value.toString(), Toast.LENGTH_SHORT).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssemblyOrderTableGoodsBinding.inflate(inflater, container, false)


        rwAdapter = AsseblyOrderTableGoodsAdapter(model.tableGoods)
        rwTableGoods = binding.root.findViewById<RecyclerView>(R.id.rwGoods)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter

        model.tableGoods.observe(viewLifecycleOwner, Observer<List<AssemblyOrderTableGoods>> { list ->
            list.let {
                rwAdapter.notifyDataSetChanged() }
        })

        return binding.root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): AssemblyOrderTableGoodsFragment {
            return AssemblyOrderTableGoodsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}