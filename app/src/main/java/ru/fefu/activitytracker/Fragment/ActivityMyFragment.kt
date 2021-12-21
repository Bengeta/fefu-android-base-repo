package ru.fefu.activitytracker.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.Adapters.RecyclerAdapter
import ru.fefu.activitytracker.DataBaseStaff.*
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Repository.MyCrossRepository
import java.util.*
import java.util.Date


class ActivityMyFragment : Fragment() {
    private var crossRepository: MutableList<DBCrossItem> = mutableListOf<DBCrossItem>()
    private var recyclerAdapter = RecyclerAdapter(crossRepository)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_activity_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // fillRecycler()
        App.INSTANCE.db.activityDao().getAll().observe(viewLifecycleOwner) {
            crossRepository.clear()
            var prev_date: Long? = null
            for (activity in it) {
                if(activity.date_end == null) continue
                val item = Activity(
                    id = activity.id,
                    type = activity.type,
                    date_start = activity.date_start,
                    date_end = activity.date_end,
                    coordinates = activity.coordinates,
                )
                if (writeDate(activity.date_start?.let { it1 -> Date(it1) })
                    == writeDate(prev_date?.let { it1 -> Date(it1) })
                )
                    crossRepository.add(item)
                else {
                    crossRepository.add(Date_(date = writeDate(activity.date_start?.let { it1 ->
                        Date(
                            it1
                        )
                    })))
                    crossRepository.add(item)
                    prev_date = activity.date_start
                }
            }
            recyclerAdapter.submitList(crossRepository)

        }

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
                        (crossRepository[it] as Activity).id
                    )
                )
                addToBackStack(null)
                commit()
            }
        }
    }


    private fun writeDate(date: Date?): String? {
        val months = listOf<String>(
            "Январь",
            "Февраль",
            "Март",
            "Апрель",
            "Май",
            "Июнь",
            "Июль",
            "Август",
            "Сентябрь",
            "Октябрь",
            "Ноябрь",
            "Декабрь"
        )
        val cal1: Calendar = Calendar.getInstance()
        if (date == null)
            return null
        cal1.time = date;
        return months[cal1.get(Calendar.MONTH)] + " " + cal1.get(Calendar.YEAR) + " года"


    }


}

