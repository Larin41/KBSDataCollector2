package ru.kbs41.kbsdatacollector.ui.stamps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.CommonFunctions
import ru.kbs41.kbsdatacollector.databinding.FragmentStampsBinding

import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrderTableStamps
import ru.kbs41.kbsdatacollector.ui.mainactivity.AllAssemblyOrdersAdapter


/**
 * A placeholder fragment containing a simple view.
 */
class StampsFragment : Fragment() {

    private var _binding: FragmentStampsBinding? = null
    private val binding get() = _binding!!


    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter

    private var qty: Double = 0.toDouble()

    private val model: StampsViewModel by activityViewModels() {
        val docId = requireActivity().intent.getLongExtra("AssemblyOrderId", 0)
        val productId = requireActivity().intent.getLongExtra("productId", 0)
        StampsViewModelFactory(docId, productId)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentStampsBinding.inflate(inflater, container, false)
        val root = binding.root

        qty = requireActivity().intent.getDoubleExtra("qty", 0.toDouble())
        binding.qty = qty.toString()

        val tableStamps = model.tableStamps.value


        model.product.observe(viewLifecycleOwner) {
            binding.product = it[0].name
            binding.qtyCollected = tableStamps?.size.toString()
        }

        model.tableStamps.observe(
            viewLifecycleOwner,
            Observer<List<AssemblyOrderTableStamps>> { list ->
                list.let {
                    binding.qtyCollected = it.size.toString()
                }
            })
        return root
    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): StampsFragment {
            return StampsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}