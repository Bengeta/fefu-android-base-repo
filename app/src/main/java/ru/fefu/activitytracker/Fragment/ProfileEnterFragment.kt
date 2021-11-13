package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R

class ProfileEnterFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_enter, container, false)
    }

    override fun onStart() {
        super.onStart()
        var view = requireView()
        val change_password = view.findViewById<TextView>(R.id.change_password)
        change_password.setOnClickListener{
            var fragment_manager = (parentFragment as FlowFragmentInterface).getFlowFragmentManager()
            fragment_manager.beginTransaction().apply {
                replace(R.id.container, ChangePasswordFragment())
                addToBackStack(null)
                commit()
            }
        }

    }

}