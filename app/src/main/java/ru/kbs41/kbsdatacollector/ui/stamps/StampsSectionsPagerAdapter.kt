package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.kbs41.kbsdatacollector.R
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderFragment
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderTableGoodsFragment
import ru.kbs41.kbsdatacollector.ui.assemblyorders.AssemblyOrderTableStampsFragment

private val TAB_TITLES = arrayOf(
    R.string.information,
    R.string.assembly_order_stamps

)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class StampsSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> StampsFragment()
            1 -> StampsFragment()
            else -> {
                StampsFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 2
    }
}