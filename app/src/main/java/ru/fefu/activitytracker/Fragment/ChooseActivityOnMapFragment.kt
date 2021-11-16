package ru.fefu.activitytracker.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.fefu.activitytracker.Adapters.RecyclerAdapter
import ru.fefu.activitytracker.Adapters.RecyclerForMapAdapter
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Repository.ActivityForMapRepository
import ru.fefu.activitytracker.Repository.MyCrossRepository


class ChooseActivityOnMapFragment : Fragment() {
    private val crossRepository = ActivityForMapRepository()
    private var chossenItem = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_choose_activity_on_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        val recyclerAdapter = RecyclerForMapAdapter(crossRepository.getCrosses(), requireActivity())
        val view = requireView()
        chossenItem = recyclerAdapter.GetSelectedItem()
        recyclerAdapter.ChangeSelection(chossenItem, -1)
        val recyclerView = view.findViewById<RecyclerView>(R.id.activity_list_for_map)
        val startButtom = view.findViewById<MaterialButton>(R.id.start_activity_map)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        startButtom.setOnClickListener {
            val fragment_manager =
                (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(
                    R.id.fragment_container_map,
                    StartActivityOnMapFragment.newInstance(chossenItem)
                )
                addToBackStack(null)
                commit()

            }
        }
        recyclerAdapter.setItemClickListener {
            recyclerAdapter.ChangeSelection(it, chossenItem)
            chossenItem = it
        }

    }


}