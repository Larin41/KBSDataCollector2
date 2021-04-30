package ru.kbs41.kbsdatacollector.ui.mainactivity.goods

import android.os.Bundle
import android.os.Debug
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.dataSources.network.ExchangeMaster

class GoodsFragment : Fragment() {

    private val viewModel: GoodsViewModel by activityViewModels()

    private lateinit var rootView: View
    private lateinit var rwGoodsAdapter: GoodsAdapter
    private lateinit var rwGoods: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        rootView = inflater.inflate(R.layout.fragment_goods, container, false)

        val swipeRefreshLayout = rootView.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshGoods)
        swipeRefreshLayout.setOnRefreshListener {
            Debug.waitForDebugger()
            ExchangeMaster.getAllGoodsFrom1C()
        }

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = "Товары"

        rwGoodsAdapter = GoodsAdapter(viewModel.allProducts)
        rwGoods = rootView.findViewById<RecyclerView>(R.id.rwProducts)
        rwGoods.layoutManager = LinearLayoutManager(context)
        rwGoods.adapter = rwGoodsAdapter


        viewModel.allProducts.observe(viewLifecycleOwner, { list ->
            list.let {
                rwGoodsAdapter.notifyDataSetChanged()
            }
        })

        return rootView

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //viewModel = ViewModelProvider(this).get(GoodsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}