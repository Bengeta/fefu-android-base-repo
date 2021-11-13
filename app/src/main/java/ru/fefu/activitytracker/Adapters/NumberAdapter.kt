package ru.fefu.activitytracker.Adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.fefu.activitytracker.Fragment.ActivityFragment
import ru.fefu.activitytracker.Fragment.ActivityMyFragment
import ru.fefu.activitytracker.Fragment.ActivityOtherFragment

class NumberAdapter(fragment: ActivityFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        val fragment = if (position == 0) ActivityMyFragment() else ActivityOtherFragment()
        return fragment
    }

}