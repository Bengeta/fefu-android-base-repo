package ru.fefu.activitytracker.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.fefu.activitytracker.Fragment.ActivityFlowFragment
import ru.fefu.activitytracker.Fragment.MapFlowFragment
import ru.fefu.activitytracker.R

class MapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
    }
}