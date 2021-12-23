package ru.fefu.activitytracker.Fragment

import android.location.Location
import android.os.Bundle
import android.os.Parcelable
import android.text.format.DateFormat
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.os.bundleOf
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.CrossItemEntity
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.Enums.CrossType
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Items.CrossItem
import ru.fefu.activitytracker.Repository.MyCrossRepository
import ru.fefu.activitytracker.Repository.OtherCrossRepository
import java.util.*
import kotlin.math.roundToLong

class DetalisationFragment() : Fragment() {
    private var is_my: Boolean = false
    private var crossRepository = mutableListOf<CrossItem>()
    private var position: Int = 0
    lateinit var cross_item: CrossItemEntity

    companion object {
        fun newInstance(position: Int, is_my: Boolean, id: Int?) =
            DetalisationFragment().apply {
                arguments =
                    bundleOf("is_my" to is_my, "position" to position, "id" to id)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        is_my = arguments?.get("is_my") as Boolean
        position = arguments?.get("position") as Int
        val id = arguments?.get("id") as Int?
        if (is_my)
            cross_item = App.INSTANCE.db.activityDao().getById(id!!)
        crossRepository = if (is_my) MyCrossRepository().getCrosses()
            .toMutableList() else OtherCrossRepository().getCrosses().toMutableList()
        return inflater.inflate(R.layout.fragment_detalisation, container, false)
    }

    override fun onStart() {
        super.onStart()
        var view = requireView()
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_detalisation)
        if (is_my) toolbar.inflateMenu(R.menu.toolbar_detalisation_menu)

        val name = view.findViewById<TextView>(R.id.name_for_detalisation)
        val distance = view.findViewById<TextView>(R.id.distance_for_detalisation)
        val time = view.findViewById<TextView>(R.id.time_activity_for_detalisation)
        val date = view.findViewById<TextView>(R.id.activity_date_for_detalisation)
        val period = view.findViewById<TextView>(R.id.time_period_activity_for_detalisation)
        if (!is_my) {
            toolbar.title = crossRepository[position].type;
            name.text = crossRepository[position].beneficial
            distance.text = crossRepository[position].distance
            time.text = crossRepository[position].time
            period.text = crossRepository[position].period
            date.text = crossRepository[position].date
        } else {
            name.text = null
            toolbar.title = CrossType.values()[cross_item.type!!].type;
            distance.text = countDistance(SerialiseClass().listDecode(cross_item.coordinates!!))
            date.text = DateFormat.format("dd.MM.yyyy", Date(cross_item.date_start!!)).toString()
            period.text = ("Старт " + DateFormat.format("hh:mm", Date(cross_item.date_start!!))
                .toString() + " | Финиш" + DateFormat.format("hh:mm", Date(cross_item.date_end!!))
                .toString())
            time.text = countPeriod(cross_item.date_end!! - cross_item.date_start!!)
        }



        toolbar.setNavigationOnClickListener {
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }


    }


    private fun countDistance(list: List<Pair<Double, Double>>): String {
        var distance: Double = 0.0

        for (i in 0..list.size - 2) {
            val startPoint = Location("locationA")
            startPoint.latitude = list[i].first
            startPoint.longitude = list[i].second

            val endPoint = Location("locationA")
            endPoint.latitude = list[i + 1].first
            endPoint.longitude = list[i + 1].second

            distance += startPoint.distanceTo(endPoint).toDouble() / 1000
        }// first - latitude second - longitude
        return distance.roundToLong().toString() + " km"
    }

    private fun countPeriod(period: Long): String {

        var period_str: String = "";
        var minute = period / 60000
        val hour = minute / 60
        minute = minute % 60;

        if (hour > 0)
            period_str = period_str + hour.toString() + " час(ов)"
        if (minute > 0)
            period_str = period_str + minute.toString() + " минут(а)"
        if (period_str == "") period_str = "Меньше минуты"
        return period_str
    }
}