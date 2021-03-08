package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.kbs41.kbsdatacollector.CommonFunctions

import ru.kbs41.kbsdatacollector.databinding.FragmentStampsInfoBinding


class StampsFragmentInfo : Fragment() {

    private var _binding: FragmentStampsInfoBinding? = null
    private val binding get() = _binding!!

    private val model: StampsViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStampsInfoBinding.inflate(inflater, container, false)
        val root = binding.root

        binding.qty = CommonFunctions.getFormattedNumber(model.getQty())

        model.product.observe(viewLifecycleOwner) {
            binding.product = model.product.value?.name
        }

        model.tableStampsWithProducts.observe(viewLifecycleOwner) {
            binding.qtyCollected = it.size.toString()
        }

        return root
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