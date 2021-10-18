package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, ActivityFragment(), "activity")
                addToBackStack("activity")
                commit()
            }

        }
        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_nav.setOnItemSelectedListener {
            if (it.itemId == R.id.bottom_menu_activity) {
                supportFragmentManager.beginTransaction().apply {
                    var f = supportFragmentManager.findFragmentByTag("activity")
                    if (f != null) this.show(f)
                    f = supportFragmentManager.findFragmentByTag("profile")
                    if (f != null) this.hide(f)
                    addToBackStack("activity")
                    commit()
                }
            }else if (it.itemId == R.id.bottom_menu_profile) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null)
                        this.hide(fragment)
                     fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment == null) add(R.id.fragmentContainerView, ProfileFragment(), "profile")
                    else this.show(fragment)

                    addToBackStack("profile")
                    commit()
                }
            }
            true
        }
    }
}