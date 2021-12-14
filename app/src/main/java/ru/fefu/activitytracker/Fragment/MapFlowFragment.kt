package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R

class MapFlowFragment : Fragment(), FlowFragmentInterface {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map_flow, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = App.INSTANCE.db.activityDao().getLast()
        if(savedInstanceState == null)
            childFragmentManager.beginTransaction().apply {
                if(activity==null)
                    replace(R.id.fragment_container_map, ChooseActivityOnMapFragment())
                else if(activity!=null && activity.date_end!=null)
                        replace(R.id.fragment_container_map, ChooseActivityOnMapFragment())
                else if( activity != null && activity.date_end==null)
                    replace(R.id.fragment_container_map, StartActivityOnMapFragment.newInstance(activity.type!!))
                commit()
            }

    }
    override fun getFlowFragmentManager(): FragmentManager = childFragmentManager
}