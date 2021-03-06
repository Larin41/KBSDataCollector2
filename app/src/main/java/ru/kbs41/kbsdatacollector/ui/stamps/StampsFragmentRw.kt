package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R

import ru.kbs41.kbsdatacollector.databinding.FragmentStampsRwBinding


class StampsFragmentRw : Fragment() {

    private var _binding: FragmentStampsRwBinding? = null
    private val binding get() = _binding!!

    private lateinit var rootView: View
    private lateinit var rwAdapter: AssemblyOrderTableStampsNoProductAdapter
    private lateinit var rwTableGoods: RecyclerView

    private var qty: Double = 0.toDouble()

    private val model: StampsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentStampsRwBinding.inflate(inflater, container, false)
        val root = binding.root

        rwAdapter = AssemblyOrderTableStampsNoProductAdapter(model.tableStampsWithProducts)
        rwTableGoods = binding.root.findViewById<RecyclerView>(R.id.rwStamps)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter

        model.tableStampsWithProducts.observe(
            viewLifecycleOwner,
            { list ->
                list.let {
                    rwAdapter.notifyDataSetChanged()
                    if (model.tableStamps.value!!.isNotEmpty()) {
                        rwTableGoods.smoothScrollToPosition(model.tableStamps.value!!.size - 1)
                    }
                }
            })

        qty = requireActivity().intent.getDoubleExtra("qty", 0.toDouble())
        binding.qty = qty.toString()

        val tableStamps = model.tableStamps.value


        model.product.observe(viewLifecycleOwner) {
            binding.qtyCollected = tableStamps?.size.toString()
        }

        model.tableStamps.observe(
            viewLifecycleOwner,
            {
                rwAdapter.notifyDataSetChanged()
                it.let {

                    binding.qtyCollected = it.size.toString()
                }
            })
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): StampsFragmentRw {
            return StampsFragmentRw().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}