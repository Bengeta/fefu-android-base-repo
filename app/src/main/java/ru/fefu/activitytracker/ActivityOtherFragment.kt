package ru.fefu.activitytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ActivityOtherFragment : Fragment() {

    private val crossRepository = OtherCrossRepository()
    private val recyclerAdapter = RecyclerAdapter(crossRepository.getCrosses(),false)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recyclerAdapter.setItemClickListener {
            var fragment_manager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(R.id.container,DetalisationFragment(it,false))
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