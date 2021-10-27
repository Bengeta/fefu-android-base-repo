package ru.fefu.activitytracker

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentContainerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.FieldPosition


class NavigationActivity : AppCompatActivity() {
    private var tab: Int = 1;
    private lateinit var bottom_nav: BottomNavigationView;

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        if (savedInstanceState != null) {
            bottom_nav.selectedItemId = savedInstanceState.getInt("tabs", 1)
        };
        else {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.fragmentContainerView, ActivityFragment(), "activity")
                commit()
            }
        }
        bottom_nav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottom_nav.setOnItemSelectedListener {
            if (it.itemId == R.id.bottom_menu_activity && bottom_nav.selectedItemId == R.id.bottom_menu_profile) {
                supportFragmentManager.beginTransaction().apply {
                    var f = supportFragmentManager.findFragmentByTag("activity")
                    if (f != null) this.show(f)
                    f = supportFragmentManager.findFragmentByTag("detalisation")
                    if (f != null)
                        this.remove(f)
                    f = supportFragmentManager.findFragmentByTag("profile")
                    if (f != null) this.hide(f)
                    addToBackStack("activity")
                    commit()
                }
            } else if (it.itemId == R.id.bottom_menu_profile && bottom_nav.selectedItemId == R.id.bottom_menu_activity) {
                supportFragmentManager.beginTransaction().apply {
                    var fragment = supportFragmentManager.findFragmentByTag("activity")
                    if (fragment != null)
                        this.hide(fragment)
                    fragment = supportFragmentManager.findFragmentByTag("detalisation")
                    if (fragment != null){
                        this.remove(fragment)
                        supportFragmentManager.popBackStack()
                    }
                    fragment = supportFragmentManager.findFragmentByTag("profile")
                    if (fragment == null) add(
                        R.id.fragmentContainerView,
                        ProfileFragment(),
                        "profile"
                    )
                    else
                        this.show(fragment)
                    addToBackStack("profile")
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

    public fun callback(position: Int, is_my: Boolean) {
        supportFragmentManager.beginTransaction().apply {
            var f = supportFragmentManager.findFragmentByTag("activity")
            if (f != null) this.hide(f)
            this.add( R.id.fragmentContainerView, DetalisationFragment(position,is_my), "detalisation")
            addToBackStack("detalisation")
            commit()
        }

    }
    public fun callbackFromDetalisation() {
        supportFragmentManager.beginTransaction().apply {
            var f = supportFragmentManager.findFragmentByTag("detalisation")
            if (f != null) this.remove(f)
            this.add( R.id.fragmentContainerView, ActivityFragment(), "activity")
            addToBackStack("activity")
            commit()
        }

    }
}