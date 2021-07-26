package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.R

import ru.kbs41.kbsdatacollector.databinding.FragmentStampsInfoBinding
import java.lang.Exception


class StampsFragmentInfo : Fragment() {

    private var _binding: FragmentStampsInfoBinding? = null
    private val binding get() = _binding!!

    lateinit var viewModel: StampsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStampsInfoBinding.inflate(inflater, container, false)
        val root = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(StampsViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        setData()
        subscribeObservers()

    }

    private fun setData() {
        binding.product = viewModel.product.name
    }

    private fun subscribeObservers() {

        viewModel.rowTableGoods.observe(viewLifecycleOwner, {
            if (viewModel.addedManually) {
                binding.qty = binding.qtyCollected
            } else {
                binding.qty = it.qty.toString()
            }
        })

        viewModel.tableStamps.observe(viewLifecycleOwner, {
            val size = it.size
            binding.qtyCollected = size.toString()
            if (viewModel.addedManually) {
                binding.qty = binding.qtyCollected
            }
            viewModel.updateRowTableGoods(size.toDouble())
        })

    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): StampsFragmentInfo {
            return StampsFragmentInfo().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}