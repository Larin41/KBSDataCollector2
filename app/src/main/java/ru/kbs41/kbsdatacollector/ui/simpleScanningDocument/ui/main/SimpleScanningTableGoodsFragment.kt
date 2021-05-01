package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.databinding.FragmentSimpleScanningInfoBinding
import ru.kbs41.kbsdatacollector.databinding.FragmentSimpleScanningTableGoodsBinding
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderTableStampsAdapter
import ru.kbs41.kbsdatacollector.ui.mainactivity.simpleScanning.SimpleScanningAdapter
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.SimpleScanningViewModel
import ru.kbs41.kbsdatacollector.ui.stamps.StampsFragmentInfo

//
class SimpleScanningTableGoodsFragment : Fragment() {

    private var _binding: FragmentSimpleScanningTableGoodsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: SimpleScanningTableGoodsAdapter
    private lateinit var rwTableGoods: RecyclerView

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSimpleScanningTableGoodsBinding.inflate(inflater, container, false)
        val root = binding.root

        val model = ViewModelProvider(requireActivity()).get(SimpleScanningViewModel::class.java)

        rwAdapter = SimpleScanningTableGoodsAdapter(model.tableGoods)
        rwTableGoods = root.findViewById(R.id.rwSimpleScanningTableGoods)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter

        model.tableGoods.observe(
            viewLifecycleOwner,
            {
                it.let {
                    rwAdapter.notifyDataSetChanged()
                }
            })

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