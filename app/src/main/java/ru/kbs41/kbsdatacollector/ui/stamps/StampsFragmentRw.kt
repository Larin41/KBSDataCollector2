package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.CommonFunctions
import ru.kbs41.kbsdatacollector.R

import ru.kbs41.kbsdatacollector.databinding.FragmentStampsRwBinding
import java.text.DecimalFormat


class StampsFragmentRw : Fragment() {

    private var _binding: FragmentStampsRwBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AssemblyOrderTableStampsNoProductAdapter
    private lateinit var rwTableGoods: RecyclerView

    private var qty: Double = 0.toDouble()

    private val model: StampsViewModel by activityViewModels()

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

        model.qtyCollected.observe(
            viewLifecycleOwner,
            {
                if(it == model.qty.value){
                    binding.tvQtyCollected.setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_700))
                } else {
                    binding.tvQtyCollected.setTextColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
        )

        model.tableStampsWithProducts.observe(
            viewLifecycleOwner,
            {
                rwAdapter.notifyDataSetChanged()
                val size = it.size
                binding.qtyCollected = size.toString()
                if (size > 0){
                    rwTableGoods.smoothScrollToPosition(size - 1)
                }
            })

        qty = requireActivity().intent.getDoubleExtra("qty", 0.0)
        binding.qty = CommonFunctions.getFormattedNumber(qty)

        return root
    }

}