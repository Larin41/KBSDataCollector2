package ru.kbs41.kbsdatacollector.ui.assemblyorders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.databinding.FragmentAssemblyOrderBinding
import ru.kbs41.kbsdatacollector.ui.mainactivity.orders.AllAssemblyOrdersAdapter
import java.lang.Exception


/**
 * A placeholder fragment containing a simple view.
 */
class AssemblyOrderFragment : Fragment() {

    private var _binding: FragmentAssemblyOrderBinding? = null
    private val binding get() = _binding!!


    private lateinit var rootView: View
    private lateinit var rwOrders: RecyclerView
    private lateinit var rwAdapterAllAssembly: AllAssemblyOrdersAdapter

    private lateinit var viewModel: AssemblyOrderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAssemblyOrderBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(AssemblyOrderViewModel::class.java)
        } ?: throw Exception("Invalid activity")

        setValues()
    }

    private fun setValues() {

        binding.date = FormatManager.getDateRussianFormat(viewModel.currentAssemblyOrder.date)
        binding.number = viewModel.currentAssemblyOrder.number
        binding.contractor = viewModel.currentAssemblyOrder.counterpart
        binding.comment = viewModel.currentAssemblyOrder.comment

        var comment: String = "---"
        if (viewModel.currentAssemblyOrder.comment.length != 0) {
            comment = viewModel.currentAssemblyOrder.comment
        }
        binding.comment = comment

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