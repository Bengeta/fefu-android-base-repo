package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Repository.ActivityForMapRepository

class StartActivityOnMapFragment : Fragment() {
    private var position: Int = 0
    private val crossRepository = ActivityForMapRepository().getCrosses().toMutableList()

    companion object {
        fun newInstance(position: Int) = StartActivityOnMapFragment().apply {
            arguments = bundleOf("position" to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        position = arguments?.get("position") as Int
        return inflater.inflate(R.layout.fragment_start_activity_on_map, container, false)
    }

    override fun onStart() {
        super.onStart()
        val view = requireView()
        val type = view.findViewById<TextView>(R.id.type_on_start_activity_map)
        val width = view.findViewById<TextView>(R.id.activity_width)
        type.text = crossRepository[position].type
        width.text = crossRepository[position].record
    }

}