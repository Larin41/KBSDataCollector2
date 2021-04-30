package ru.kbs41.kbsdatacollector.ui.simpleScanningDocument.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.stamps.StampsFragmentInfo
import ru.kbs41.kbsdatacollector.ui.stamps.StampsFragmentRw

private val TAB_TITLES = arrayOf(
    R.string.assembly_order_stamps,
    R.string.information
)


class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SimpleScanningInfo()
            1 -> SimpleScanningInfo()
            else -> {
                SimpleScanningInfo()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}