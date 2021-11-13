package ru.fefu.activitytracker.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.fefu.activitytracker.Fragment.ActivityFlowFragment
import ru.fefu.activitytracker.Fragment.ProfileFlowFragment
import ru.fefu.activitytracker.R


class NavigationActivity : AppCompatActivity() {
    private var tab: Int = 1;
    private lateinit var bottom_nav: BottomNavigationView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        if (savedInstanceState != null) {
            bottom_nav.selectedItemId = savedInstanceState.getInt("tabs", 1)
        };
        else  {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.fragmentContainerView, ActivityFlowFragment(),"activity")
                commit()
            }
        }
        bottom_nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_nav.setOnItemSelectedListener {
            if (it.itemId == R.id.bottom_menu_activity && bottom_nav.selectedItemId == R.id.bottom_menu_profile) {
                supportFragmentManager.beginTransaction().apply {
                    var f = supportFragmentManager.findFragmentByTag("activity")
                    if (f != null) this.show(f)
                    f = supportFragmentManager.findFragmentByTag("profile")
                    if (f != null) this.hide(f)
                    commit()
                }
            } else if (it.itemId == R.id.bottom_menu_profile && bottom_nav.selectedItemId == R.id.bottom_menu_activity) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null)
                        this.hide(fragment)
                    fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment == null) add(
                        R.id.fragmentContainerView,
                        ProfileFlowFragment(),
                        "profile"
                    )
                    else this.show(fragment)
                    commit()
                }
            }
            true
        }

    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("tabs", bottom_nav.selectedItemId)
    }
}