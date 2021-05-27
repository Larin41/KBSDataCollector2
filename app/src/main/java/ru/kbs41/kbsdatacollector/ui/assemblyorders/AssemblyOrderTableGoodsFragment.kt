package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderTableGoodsBinding
import java.lang.Exception


/**
 * A placeholder fragment containing a simple view.
 */
class AssemblyOrderTableGoodsFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderTableGoodsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rwAdapter: AssemblyOrderTableGoodsAdapter
    private lateinit var rwTableGoods: RecyclerView

    private lateinit var viewModel: AssemblyOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssemblyOrderTableGoodsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(AssemblyOrderViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        subscribeObservers()

        setRecyclerView()

    }

    private fun setRecyclerView() {
        rwAdapter = AssemblyOrderTableGoodsAdapter(
            requireContext(),
            viewModel,
            viewModel.tableQtyQtyCollected
        )
        rwTableGoods = binding.root.findViewById(R.id.rwGoods)
        rwTableGoods.layoutManager = LinearLayoutManager(context)
        rwTableGoods.adapter = rwAdapter
    }

    private fun subscribeObservers() {
        viewModel.tableQtyQtyCollected.observe(
            viewLifecycleOwner,
            {
                rwAdapter.notifyDataSetChanged()
            })
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