package ru.fefu.activitytracker.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers.Main
import ru.fefu.activitytracker.Activity.EnterActivity
import ru.fefu.activitytracker.Activity.MapActivity
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Adapters.NumberAdapter
import ru.fefu.activitytracker.R

class ActivityFragment : Fragment(), FlowFragmentInterface {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: NumberAdapter
    private val tabNames : Array<String> = arrayOf("Мои","Пользователей")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NumberAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = adapter
        tabLayout = view.findViewById(R.id.tab_layout)

        TabLayoutMediator(tabLayout,viewPager){
                tab,position->tab.text = tabNames[position]
        }.attach()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) return
        val activity_tab = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).menu.findItem(
            R.id.bottom_menu_activity
        )
        if(!activity_tab.isChecked) activity_tab.isChecked = true
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        val button_start_activity = view.findViewById<FloatingActionButton>(R.id.button_activity_start)
        button_start_activity.setOnClickListener{
            startActivity(Intent(requireContext(), MapActivity::class.java))
        }
    }

    override fun getFlowFragmentManager(): FragmentManager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
}