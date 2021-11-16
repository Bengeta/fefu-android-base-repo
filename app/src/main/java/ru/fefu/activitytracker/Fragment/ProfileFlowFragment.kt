package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R

class ProfileFlowFragment : Fragment(), FlowFragmentInterface {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                R.id.bottom_menu_profile
            )
        profile_tab.isChecked = true
        return inflater.inflate(R.layout.fragment_flow_profile, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null)
            childFragmentManager.beginTransaction().apply {
                replace(R.id.container, ProfileEnterFragment())
                commit()
            }

    }
    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) return
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                R.id.bottom_menu_profile
            )
        if (!profile_tab.isChecked) profile_tab.isChecked = true
    }
    override fun getFlowFragmentManager(): FragmentManager = childFragmentManager
}