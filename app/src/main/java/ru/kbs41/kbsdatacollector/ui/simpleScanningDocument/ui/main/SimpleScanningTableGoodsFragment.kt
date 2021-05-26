package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.databinding.FragmentSimpleScanningTableGoodsBinding
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.SimpleScanningViewModel
import ru.kbs41.kbsdatacollector.ui.stamps.StampsFragmentInfo
import java.lang.Exception


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
                    GlobalScope.launch(Dispatchers.Main) {
                        rwAdapter.notifyDataSetChanged()

                        val lastestPosition = model.getLastestPosition()
                        rwTableGoods.scrollToPosition(lastestPosition)
                        //Debug.waitForDebugger()
                        delay(1000)
                        val currentView =
                            rwTableGoods.findViewHolderForAdapterPosition(lastestPosition)?.itemView
                        Log.d("CurrentView", currentView.toString())

                        try {
                            currentView?.setBackgroundColor(Color.GREEN)
                            delay(600)
                            currentView?.setBackgroundColor(resources.getColor(R.color.gray))
                        } catch (e: Exception){
                            Log.d("CurrentView", e.toString())
                        } finally {

                        }


                    }


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