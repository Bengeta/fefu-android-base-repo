package ru.fefu.activitytracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ActivityMyFragment : Fragment() {
    private val crossRepository = MyCrossRepository()
    private val recyclerAdapter = RecyclerAdapter(crossRepository.getCrosses(),true)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter.setItemClickListener {
            var fragment_manager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                var f = fragment_manager.findFragmentByTag("activitys")
                if (f != null)
                    this.remove(f)
                replace(R.id.container,DetalisationFragment(it,true))
                commit()
            }
        }
        return inflater.inflate(R.layout.fragment_activity_my, container, false)
    }


    override fun onStart() {
        super.onStart()
        val recyclerView   = requireView().findViewById<RecyclerView>(R.id.rv_date_cross)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }



}