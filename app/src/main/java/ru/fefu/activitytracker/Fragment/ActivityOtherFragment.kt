package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.Adapters.AdapterForOther
import ru.fefu.activitytracker.Adapters.RecyclerAdapter
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Repository.OtherCrossRepository


class ActivityOtherFragment : Fragment() {

    private val crossRepository = OtherCrossRepository()
    private val recyclerAdapter = AdapterForOther(crossRepository.getCrosses(),false)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter.setItemClickListener {
            var fragment_manager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(R.id.container, DetalisationFragment.newInstance(it, false,null))
                addToBackStack(null)
                commit()
            }
        }
        return inflater.inflate(R.layout.fragment_activity_other, container, false)

}
    override fun onStart() {
        super.onStart()
        val recyclerView   = requireView().findViewById<RecyclerView>(R.id.rv_date_cross_other)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }
}