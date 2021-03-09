package ru.kbs41.kbsdatacollector.ui.stamps

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.kbs41.kbsdatacollector.R

private val TAB_TITLES = arrayOf(
    R.string.assembly_order_stamps,
    R.string.information

)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class StampsSectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> StampsFragmentRw()
            1 -> StampsFragmentInfo()
            else -> {
                StampsFragmentRw()
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