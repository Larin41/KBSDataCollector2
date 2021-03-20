package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.CommonFunctions
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderBinding
import ru.kbs41.kbsdatacollector.room.db.AssemblyOrder
import ru.kbs41.kbsdatacollector.ui.mainactivity.orders.AllAssemblyOrdersAdapter


/**
 * A placeholder fragment containing a simple view.
 */
class AssemblyOrderFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderBinding? = null
    private val binding get() = _binding!!


    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter

    private val model: AssemblyOrderViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssemblyOrderBinding.inflate(inflater, container, false)

        binding.date = CommonFunctions.getDateRussianFormat(model.currentAssemblyOrder.date)
        binding.number = model.currentAssemblyOrder.number
        binding.contractor = model.currentAssemblyOrder.counterpart
        binding.comment = model.currentAssemblyOrder.comment

        var comment: String = "---"
        if (model.currentAssemblyOrder.comment.length != 0) {
            comment = model.currentAssemblyOrder.comment
        }
        binding.comment = comment

        return binding.root

    }

    companion object {
        private const val ARG_SECTION_NUMBER = "section_number"

        @JvmStatic
        fun newInstance(sectionNumber: Int): AssemblyOrderFragment {
            return AssemblyOrderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}