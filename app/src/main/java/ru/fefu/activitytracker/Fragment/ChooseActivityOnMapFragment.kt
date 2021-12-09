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
        super.onStart()
        isPermisiion();
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
            if (isPermisiion()) {

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

            val list = mutableListOf<Pair<Double, Double>>()
            for (i in 1..5)
                list.add(Pair(random() * 10, random() * 10))

            App.INSTANCE.db.activityDao().insertCross(
                CrossItemEntity(
                    id = 0,
                    type = crossRepository.getCrosses().toMutableList()[chossenItem].type,
                    date_start = System.currentTimeMillis(),
                    date_end = System.currentTimeMillis() + 1000000000,
                    coordinates = SerialiseClass().listEncode(list),
                )
            )

             val intent = Intent(requireActivity(),RecordLocationService::class.java)
             ContextCompat.startForegroundService(requireActivity(),intent)
        }

         }
        recyclerAdapter.setItemClickListener {
            recyclerAdapter.ChangeSelection(it, chossenItem)
            chossenItem = it
        }

    }



    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            //Разрешение выдали
            if (!granted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                    && !shouldShowRequestPermissionRationale(android.Manifest.permission.ACCESS_FINE_LOCATION)
                ) {
                    //Разрашение уже запрашивали, не выдали, и уже объясняли юзеру зачем нужно это разрешение
                    showPermissionDeniedDialog()
                } else {
                    showRationaleDialog()
                }
            }
        }

    private fun isPermisiion(): Boolean {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true
        requestCallPermissionAndCall()
        return (ContextCompat.checkSelfPermission(
            requireActivity(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                )
    }

    private fun requestCallPermissionAndCall() {
        when {
            //В случае если разрешение уже просили, но его не выдали, и нужно объяснить юзеру зачем нужно разрешение
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && shouldShowRequestPermissionRationale(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) -> {
                showRationaleDialog()
            }
            //Иначе попробовать запросить разрешение
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Permission required")
            .setMessage("You cannot call from app without call permission")
            .setPositiveButton("Proceed") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireActivity())
            .setTitle("Permission required")
            .setMessage("Please allow permission from app settings")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", activity?.packageName, null)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }


}