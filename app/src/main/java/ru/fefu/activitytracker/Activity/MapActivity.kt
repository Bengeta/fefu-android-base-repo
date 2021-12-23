package ru.fefu.activitytracker.Activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import org.osmdroid.views.overlay.Polyline
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import ru.fefu.activitytracker.Fragment.ActivityFlowFragment
import ru.fefu.activitytracker.Fragment.MapFlowFragment

import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Overlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.R
import ru.fefu.activitytracker.Services.RecordLocationService
import java.util.*


class MapActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR = 1337
        private const val REQUEST_CODE_RESOLVE_GPS_ERROR = 1338
    }

    lateinit var mapView: MapView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, getPreferences(Context.MODE_PRIVATE))

        permissionRequestLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )

        setContentView(R.layout.activity_map)
        mapView = findViewById<MapView>(R.id.map_activity)
        initMap()




    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR || requestCode == REQUEST_CODE_RESOLVE_GPS_ERROR) {
            if (resultCode == Activity.RESULT_OK) startLocationService()
        }
    }

    private fun initMap() {
        mapView.minZoomLevel = 4.0
        // post положит выполнение кода внутри в очередь,
        // что позволит выполнить этот код после полной инициализации mapView
        mapView.post {
            mapView.zoomToBoundingBox(
                BoundingBox(
                    43.232111,
                    132.117062,
                    42.968866,
                    131.768039
                ),
                false
            )
        }
        var cross = App.INSTANCE.db.activityDao().getLast()
        if(cross!= null)
            if (cross.date_end == null && cross.coordinates != null) {
                var coordinates = SerialiseClass().listDecode(cross.coordinates!!)
                for (coordinate in coordinates)
                    polyline.addPoint(GeoPoint(coordinate.first, coordinate.second))
            }
        mapView.overlayManager.add(polyline)
    }


    private val permissionRequestLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            permissions[Manifest.permission.ACCESS_FINE_LOCATION]?.let {
                if (it) {
                    showUserLocation()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }

    val polyline by lazy {
        Polyline().apply {
            outlinePaint.color = ContextCompat.getColor(
                this@MapActivity,
                R.color.purple_700
            )
        }
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
        AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("You cannot call from app without call permission")
            .setPositiveButton("Proceed") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("Please allow permission from app settings")
            .setPositiveButton("Settings") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts("package", this.packageName, null)
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .show()
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

    fun isPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true
        requestCallPermissionAndCall()
        return (ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                )
    }


    private fun showUserLocation() {
        // val locationOverlay = MyLocationNewOverlay(mapView)

        val locationOverlay = MyLocationNewOverlay(
            object : GpsMyLocationProvider(applicationContext) {
                private var mapMoved = false
                override fun onLocationChanged(location: Location) {
                    // Location class doesn't has constructor for bearing remove
                    // With bearing mapView ignores hotspot & draws center of icon in center
                    // of user location, but we need to draw bottom of icon on user location
                    location.removeBearing()
                    super.onLocationChanged(location)
                    if (mapMoved) return
                    mapMoved = true
                    mapView.controller.animateTo(
                        GeoPoint(
                            location.latitude,
                            location.longitude
                        ),
                        16.0,
                        1000
                    )
                }
            },
            mapView
        )
        val locationIcon = BitmapFactory.decodeResource(resources, R.drawable.location)
        locationOverlay.setDirectionArrow(locationIcon, locationIcon)
        if (locationIcon != null) {
            locationOverlay.setPersonHotspot(locationIcon.width / 2f, locationIcon.height.toFloat())
        }

        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)
    }


    private fun isGooglePlayServicesAvailable(): Boolean {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val result = googleApiAvailability.isGooglePlayServicesAvailable(this)
        if (result == ConnectionResult.SUCCESS) return true
        if (googleApiAvailability.isUserResolvableError(result)) {
            googleApiAvailability.getErrorDialog(
                this,
                result,
                REQUEST_CODE_RESOLVE_GOOGLE_API_ERROR
            )?.show()
        } else {
            Toast.makeText(
                this,
                "Google services unavailable",
                Toast.LENGTH_SHORT
            ).show()
        }
        return false
    }

    private fun checkIfGpsEnabled(success: () -> Unit, error: (Exception) -> Unit) {
        LocationServices.getSettingsClient(this)
            .checkLocationSettings(
                LocationSettingsRequest.Builder()
                    .addLocationRequest(RecordLocationService.locationRequest)
                    .build()
            )
            .addOnSuccessListener { success.invoke() }
            .addOnFailureListener { error.invoke(it) }
    }

    fun startLocationService(): Boolean {
        // todo check permissions & request if needed
        var result = true
        if (isGooglePlayServicesAvailable()) {
            checkIfGpsEnabled(
                {
                    RecordLocationService.startForeground(
                        this,
                        App.INSTANCE.db.activityDao().getLastId()
                    )
                },
                {
                    if (it is ResolvableApiException) {
                        it.startResolutionForResult(this, REQUEST_CODE_RESOLVE_GPS_ERROR)
                    } else {
                        Toast.makeText(this, "GPS Unavailable", Toast.LENGTH_SHORT).show()
                    }
                    result = false
                }
            )
        } else
            result = false
        return result
    }
}