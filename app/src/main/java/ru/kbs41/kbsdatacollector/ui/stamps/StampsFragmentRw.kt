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
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.R

import ru.kbs41.kbsdatacollector.databinding.FragmentStampsRwBinding
import java.lang.Exception


class StampsFragmentRw : Fragment() {

    private var _binding: FragmentStampsRwBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AssemblyOrderTableStampsNoProductAdapter
    private lateinit var rwTableGoods: RecyclerView

    private var qty: Double = 0.toDouble()

    lateinit var viewModel: StampsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentStampsRwBinding.inflate(inflater, container, false)
        val root = binding.root

        qty = requireActivity().intent.getDoubleExtra("qty", 0.0)
        binding.qty = FormatManager.getFormattedNumber(qty)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(StampsViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        subscribeObservers()
        fetchDataToRw()

    }

    private fun fetchDataToRw() {
        rwAdapter = AssemblyOrderTableStampsNoProductAdapter(viewModel.tableStampsWithProducts)
        rwTableGoods = binding.root.findViewById(R.id.rwStamps)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter
    }

    private fun subscribeObservers() {

        viewModel.qtyCollected.observe(
            viewLifecycleOwner,
            { qtyCollected ->
                if (qtyCollected == viewModel.qty.value) {
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
        )

        viewModel.tableStampsWithProducts.observe(
            viewLifecycleOwner,
            {
                rwAdapter.notifyDataSetChanged()
                val size = it.size
                binding.qtyCollected = size.toString()
                if (size > 0) {
                    rwTableGoods.smoothScrollToPosition(size - 1)
                }
            })
    }

}