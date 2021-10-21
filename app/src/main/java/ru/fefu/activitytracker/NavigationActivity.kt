package ru.fefu.activitytracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView

private var tab: Int = 1;
class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        if (savedInstanceState != null) {
            tab  = savedInstanceState.getInt("tabs",1)//1- activity 2- profile
        };
        if (tab == 1) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, ActivityFragment(), "activity")
                addToBackStack("activity")
                commit()
            }

        }
        else {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, ActivityFragment(), "activity")
                addToBackStack("profile")
                tab = 2
                commit()
            }
        }
        val bottom_nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_nav.setOnItemSelectedListener {
            if (it.itemId == R.id.bottom_menu_activity && tab == 2) {
                supportFragmentManager.beginTransaction().apply {
                    var f = supportFragmentManager.findFragmentByTag("activity")
                    if (f != null) this.show(f)
                    f = supportFragmentManager.findFragmentByTag("profile")
                    if (f != null) this.hide(f)
                    addToBackStack("activity")
                    tab = 1
                    commit()
                }
            }else if (it.itemId == R.id.bottom_menu_profile && tab == 1) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null)
                        this.hide(fragment)
                     fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment == null) add(R.id.fragmentContainerView, ProfileFragment(), "profile")
                    else this.show(fragment)
                    addToBackStack("profile")
                    tab = 2
                    commit()
                }
            }
            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("tabs",tab)
    }
}