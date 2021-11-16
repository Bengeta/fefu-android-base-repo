package ru.fefu.activitytracker.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.fefu.activitytracker.Interface.FlowFragmentInterface
import ru.fefu.activitytracker.R

class ChangePasswordFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onStart() {
        super.onStart()
        var view = requireView()
        val toolbar = view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar_change_password)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_detalisation)
        toolbar.title = getString(R.string.change_password)
        toolbar.setNavigationOnClickListener {
            (parentFragment as FlowFragmentInterface).getFlowFragmentManager().popBackStack()
        }
    }
}