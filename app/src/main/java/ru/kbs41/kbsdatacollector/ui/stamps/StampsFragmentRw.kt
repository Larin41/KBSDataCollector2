package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.databinding.FragmentStampsRwBinding


class StampsFragmentRw : Fragment() {

    private var _binding: FragmentStampsRwBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AssemblyOrderTableStampsNoProductAdapter
    private lateinit var rwTableGoods: RecyclerView

    lateinit var viewModel: StampsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStampsRwBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(StampsViewModel::class.java)

        subscribeObservers()
        fetchDataToRw()

    }

    private fun fetchDataToRw() {
        rwAdapter = AssemblyOrderTableStampsNoProductAdapter(viewModel.tableStamps)
        rwTableGoods = binding.root.findViewById(R.id.rwStamps)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter
    }

    private fun subscribeObservers() {

        viewModel.rowTableGoods.observe(viewLifecycleOwner, { value ->
            binding.qty = FormatManager.getFormattedNumber(value.qty)
            calculateQty(value.qty)
        })

        viewModel.tableStamps.observe(viewLifecycleOwner, { value ->
            binding.qtyCollected = FormatManager.getFormattedNumber(value.size.toDouble())
            rwAdapter.notifyDataSetChanged()

            if (viewModel.addedManually) {
                binding.qty = binding.qtyCollected
            }

            manageQtyColor()
        })


    }

    private fun calculateQty(qty: Double) {

        if (viewModel.addedManually) {
            binding.qty = binding.qtyCollected
        } else {
            binding.qty = FormatManager.getFormattedNumber(qty)
        }

        manageQtyColor()

    }

    private fun manageQtyColor() {
        if (binding.qty.equals(binding.qtyCollected)) {
            binding.tvQtyCollected.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.teal_700
                )
            )
        } else {
            binding.tvQtyCollected.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.red
                )
            )
        }
    }


}