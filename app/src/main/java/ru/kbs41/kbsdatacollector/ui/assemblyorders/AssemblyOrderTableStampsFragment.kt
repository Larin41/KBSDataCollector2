package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderTableGoodsBinding
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderTableStampsBinding

class AssemblyOrderTableStampsFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderTableStampsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AssemblyOrderTableStampsAdapter
    private lateinit var rwTableGoods: RecyclerView

    private val model: AssemblyOrderViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAssemblyOrderTableStampsBinding.inflate(inflater, container, false)

        rwAdapter = AssemblyOrderTableStampsAdapter(model.assemblyOrderTableStampsWithProducts)
        rwTableGoods = binding.root.findViewById(R.id.rwAllStampsInDoc)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter

        model.assemblyOrderTableStampsWithProducts.observe(
            viewLifecycleOwner,
            {
                it.let {
                    rwAdapter.notifyDataSetChanged()
                }
            })


        return binding.root
    }

    companion object {

        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): AssemblyOrderTableStampsFragment {
            return AssemblyOrderTableStampsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}