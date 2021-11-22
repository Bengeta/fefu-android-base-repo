package ru.fefu.activitytracker.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.fefu.activitytracker.Adapters.RecyclerForMapAdapter
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.CrossItemEntity
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Repository.ActivityForMapRepository
import java.lang.Math.random
import java.util.*


class ChooseActivityOnMapFragment : Fragment() {
    private val crossRepository = ActivityForMapRepository()
    private var chossenItem = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_choose_activity_on_map, container, false)
    }

    @SuppressLint("SimpleDateFormat")
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
            // если дата нового активити не совпадает то приписываем перед табличкой активити новую дату
            val activities = App.INSTANCE.db.activityDao().getAll_()
            if (activities.isNotEmpty()) {
                val today = App.INSTANCE.db.activityDao().getAll_().last().date_start
                val date = Date(today!!)
                if (!datesComparator(date, Date(System.currentTimeMillis()))) {
                    writeDate(Date(System.currentTimeMillis()))
                }
            } else
                writeDate(Date(System.currentTimeMillis()))

            val list = mutableListOf<Pair<Double,Double>>()
            for(i in 1..5)
                list.add(Pair(random() * 10,random() * 10))

            App.INSTANCE.db.activityDao().insertCross(
                CrossItemEntity(
                    id = 0,
                    type = crossRepository.getCrosses().toMutableList()[chossenItem].type,
                    date_start = System.currentTimeMillis(),
                    date_end = System.currentTimeMillis() + 1000000000,
                    coordinates = SerialiseClass().listEncode(list),
                )
            )

        }
        recyclerAdapter.setItemClickListener {
            recyclerAdapter.ChangeSelection(it, chossenItem)
            chossenItem = it
        }

    }

    private fun datesComparator(date_1: Date, date_2: Date): Boolean {
        val cal1: Calendar = Calendar.getInstance()
        val cal2: Calendar = Calendar.getInstance()
        cal1.time = date_1;
        cal2.time = date_2;
        return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
    }

    private fun writeDate(date: Date) {
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
        cal1.time = date;
        App.INSTANCE.db.activityDao().insertCross(
            CrossItemEntity(
                id = 0,
                date = months[cal1.get(Calendar.MONTH)] + " " + cal1.get(Calendar.YEAR) + " года"
            )
        )
    }


}