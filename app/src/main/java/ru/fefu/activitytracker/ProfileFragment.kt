package ru.fefu.activitytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                R.id.bottom_menu_profile)
        profile_tab.isChecked = true
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        if (hidden) return
        val profile_tab =
            requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
                R.id.bottom_menu_profile
            )
        if (!profile_tab.isChecked) profile_tab.isChecked = true
    }
    public fun setupNavigation(){
        findNavController().navigate(R.id.action_profileFragment_to_activityFragment)
    }
}