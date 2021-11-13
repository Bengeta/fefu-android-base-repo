package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.core.os.bundleOf
import ru.fefu.activitytracker.*
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.Items.CrossItem
import ru.fefu.activitytracker.Repository.MyCrossRepository
import ru.fefu.activitytracker.Repository.OtherCrossRepository

class DetalisationFragment() : Fragment() {
    private var is_my : Boolean = false
    private var crossRepository = mutableListOf<CrossItem>()
    private var  position : Int = 0

    companion object {
        fun newInstance(position: Int, is_my: Boolean) = DetalisationFragment().apply {
            arguments = bundleOf("is_my" to is_my,"position" to position,)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         is_my = arguments?.get("is_my") as Boolean
         position = arguments?.get("position") as Int
         crossRepository = if (is_my) MyCrossRepository().getCrosses()
            .toMutableList() else OtherCrossRepository().getCrosses().toMutableList()
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
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }



}
}