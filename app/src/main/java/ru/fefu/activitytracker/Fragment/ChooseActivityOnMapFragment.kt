package ru.fefu.activitytracker.Fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.audiofx.BassBoost
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ru.fefu.activitytracker.Activity.MapActivity
import ru.fefu.activitytracker.Adapters.RecyclerForMapAdapter
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.CrossItemEntity
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Repository.ActivityForMapRepository
import ru.fefu.activitytracker.Services.RecordLocationService
import java.lang.Math.random
import java.security.AccessController.checkPermission
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
        var activity = requireActivity() as MapActivity
        super.onStart()
        //activity.isPermission();
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
            if (activity.isPermission() && activity.startLocationService()) {

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

            App.INSTANCE.db.activityDao().insertCross(
                CrossItemEntity(
                    id = 0,
                    type = crossRepository.getCrosses().toMutableList()[chossenItem].type,
                    date_start = System.currentTimeMillis(),
                    date_end = null,
                    coordinates = null,
                )
            )
        }

         }
        recyclerAdapter.setItemClickListener {
            recyclerAdapter.ChangeSelection(it, chossenItem)
            chossenItem = it
        }

    }



}