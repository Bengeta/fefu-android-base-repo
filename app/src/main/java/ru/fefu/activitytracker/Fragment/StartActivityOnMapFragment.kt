package ru.fefu.activitytracker.Fragment

import android.content.Intent
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Polyline
import ru.fefu.activitytracker.Activity.MapActivity
import ru.fefu.activitytracker.Activity.NavigationActivity
import ru.fefu.activitytracker.DataBaseStaff.*
import ru.fefu.activitytracker.Enums.CrossType
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Services.RecordLocationService
import kotlin.math.roundToLong

class StartActivityOnMapFragment : Fragment() {
    lateinit var CrossItem: CrossItemEntity

    companion object {
        fun newInstance(position: Int) = StartActivityOnMapFragment().apply {
            arguments = bundleOf("position" to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start_activity_on_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val view = requireView()
        val type = view.findViewById<TextView>(R.id.type_on_start_activity_map)
        val width = view.findViewById<TextView>(R.id.activity_width)


        App.INSTANCE.db.activityDao().getLastInTime().observe(viewLifecycleOwner) {
            type.text = CrossType.values()[it.type!!].type
            if (it.coordinates != null) {
                var coordinates = it.coordinates.let { SerialiseClass().listDecode(it) }
                width.text =
                    countDistance(coordinates)
                (requireActivity() as MapActivity).polyline.addPoint(GeoPoint(coordinates.last().first, coordinates.last().second))
            }
            else
                width.text = "0"


            val finish_button = view.findViewById<FloatingActionButton>(R.id.finish_cross_button)
            finish_button.setOnClickListener {
                var activity = requireActivity()
                var intent = Intent(activity, RecordLocationService::class.java).apply {
                    action = RecordLocationService.ACTION_CANCEL
                }
                activity.startService(intent)
                intent = Intent(activity, NavigationActivity::class.java)
                activity.startActivity(intent)
            }
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

            distance = startPoint.distanceTo(endPoint).toDouble() / 1000
        }// first - latitude second - longitude
        return distance.roundToLong().toString() + " km"
    }

}