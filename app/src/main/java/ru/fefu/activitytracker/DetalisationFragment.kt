package ru.fefu.activitytracker

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import java.lang.ProcessBuilder.Redirect.to
import java.text.FieldPosition

class DetalisationFragment(position: Int, is_my: Boolean) : Fragment() {
    private val is_my = is_my
    private val crossRepository = if (is_my) MyCrossRepository().getCrosses()
        .toMutableList() else OtherCrossRepository().getCrosses().toMutableList()
    private val position = position

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detalisation, container, false)
    }

    override fun onStart() {
        super.onStart()
        var view = requireView()
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_detalisation)
        toolbar.title = crossRepository[position].type;
        if (is_my) toolbar.inflateMenu(R.menu.toolbar_detalisation_menu)

        val name = view.findViewById<TextView>(R.id.name_for_detalisation)
        val distance = view.findViewById<TextView>(R.id.distance_for_detalisation)
        val time = view.findViewById<TextView>(R.id.time_activity_for_detalisation)
        val date = view.findViewById<TextView>(R.id.activity_date_for_detalisation)
        val period = view.findViewById<TextView>(R.id.time_period_activity_for_detalisation)
        name.text = crossRepository[position].beneficial
        distance.text = crossRepository[position].distance
        time.text = crossRepository[position].time
        period.text = crossRepository[position].period
        date.text = crossRepository[position].date


        toolbar.setNavigationOnClickListener {
            val fragment_manager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(R.id.container,ActivityFragment())
                commit()
            }
        }



}
}