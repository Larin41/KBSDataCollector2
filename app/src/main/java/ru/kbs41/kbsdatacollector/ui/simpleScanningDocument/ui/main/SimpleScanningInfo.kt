package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main


import android.os.Bundle
import android.os.Debug
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.dataBase.FormatManager
import ru.kbs41.kbsdatacollector.databinding.FragmentSimpleScanningInfoBinding
import ru.kbs41.kbsdatacollector.databinding.FragmentStampsInfoBinding
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.SimpleScanningViewModel
import ru.kbs41.kbsdatacollector.ui.stamps.StampsFragmentInfo
import ru.kbs41.kbsdatacollector.ui.stamps.StampsViewModel

class SimpleScanningInfo : Fragment() {

    private var _binding: FragmentSimpleScanningInfoBinding? = null
    private val binding get() = _binding!!

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSimpleScanningInfoBinding.inflate(inflater, container, false)
        val root = binding.root

        val model = ViewModelProvider(requireActivity()).get(SimpleScanningViewModel::class.java)

        model.id.observe(viewLifecycleOwner, {
            binding.number = it.toString()
        })

        model.date.observe(viewLifecycleOwner, {
            binding.date = FormatManager.getDateRussianFormat(it)
        })

        binding.etComment.addTextChangedListener {
            model.updateComment(it.toString())
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