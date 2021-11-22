package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.Adapters.RecyclerAdapter
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.CrossItemEntity
import ru.fefu.activitytracker.DataBaseStaff.MyDataBase
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Repository.MyCrossRepository


class ActivityMyFragment : Fragment() {
    lateinit var crossRepository: MutableList<CrossItemEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_my, container, false)
    }


    override fun onStart() {
        super.onStart()
        crossRepository = mutableListOf<CrossItemEntity>()
        fillRecycler()
        val recyclerAdapter = RecyclerAdapter(crossRepository, true)
        val recyclerView = requireView().findViewById<RecyclerView>(R.id.rv_date_cross)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerAdapter.setItemClickListener {
            var fragment_manager =
                (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(
                    R.id.container,
                    DetalisationFragment.newInstance(
                        it,
                        true,
                        SerialiseClass().itemEncode(crossRepository[it])
                    )
                )
                addToBackStack(null)
                commit()
            }
        }
    }

    private fun fillRecycler() {
        crossRepository.clear()
        val all = App.INSTANCE.db.activityDao().getAll_().reversed()
        var prev_place = 0;
        for (i in all.indices)
            if (all[i].date != null) {
                crossRepository.add(all[i])
                for (j in prev_place..i) {
                    if (all[j].date != null) {
                        prev_place = j + 1
                        break
                    }
                    crossRepository.add(all[j])
                }
            }
    }


}

