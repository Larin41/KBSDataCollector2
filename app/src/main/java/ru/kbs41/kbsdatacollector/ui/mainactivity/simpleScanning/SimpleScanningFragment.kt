package ru.kbs41.kbsdatacollector.ui.mainactivity.simpleScanning

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderActivity
import ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.SimpleScanningDocument


class SimpleScanningFragment : Fragment() {

    private val viewModel: SimpleScanningViewModel by activityViewModels()

    private lateinit var rootView: View

    private lateinit var simpleScanningAdapter: SimpleScanningAdapter
    private lateinit var rwSimpleScanning: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_simple_scanning, container, false)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Сканирование"

        val fabAddNewSimpleScanning =
            rootView.findViewById<FloatingActionButton>(R.id.fabAddNewSimpleScanning)

        fabAddNewSimpleScanning.setOnClickListener {
            val intent = Intent(context, SimpleScanningDocument::class.java)
            ContextCompat.startActivity(requireContext(), intent, null)
        }

        simpleScanningAdapter = SimpleScanningAdapter(viewModel.allScannings)
        rwSimpleScanning = rootView.findViewById<RecyclerView>(R.id.rwProducts)
        rwSimpleScanning.layoutManager = LinearLayoutManager(context)
        rwSimpleScanning.adapter = simpleScanningAdapter


        viewModel.allScannings.observe(viewLifecycleOwner, { list ->
            list.let {
                simpleScanningAdapter.notifyDataSetChanged()
            }
        })

        return rootView
    }

}