package ru.fefu.activitytracker.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.Enums.CrossType
import ru.fefu.activitytracker.Items.ActivityItemForMap
import ru.fefu.activitytracker.Items.CrossItem
import ru.fefu.activitytracker.R

class RecyclerForMapAdapter(crosses: List<ActivityItemForMap>, context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context = context
    private var itemClickListener: (Int) -> Unit = {}
    private val mutable_crosses = crosses.toMutableList()

    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val type: TextView = itemView.findViewById(R.id.type_activity_for_map)

        init {
            itemView.setOnClickListener() {
                val position = absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }

        fun bind(activity_item_for_map: ActivityItemForMap) {
            type.text =CrossType.values()[activity_item_for_map.type].type


            itemView.background = if (activity_item_for_map.isSelected) ContextCompat.getDrawable(
                context,
                R.drawable.border
            )
            else ContextCompat.getDrawable(context, R.color.white)

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val veiw = LayoutInflater.from(parent.context)
            .inflate(R.layout.activiti_item_for_map, parent, false)
        return RecyclerViewHolder(veiw)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RecyclerViewHolder).bind(mutable_crosses[position])
    }

    override fun getItemCount(): Int = mutable_crosses.size


    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    fun ChangeSelection(new_position: Int, prev_position: Int) {
        if (prev_position != -1) {
            mutable_crosses[prev_position].isSelected = false
            notifyItemChanged(prev_position)
        }
        if (new_position != -1) {
            mutable_crosses[new_position].isSelected = true
            notifyItemChanged(new_position)
        }

    }

    fun GetSelectedItem(): Int {
        for (i in mutable_crosses.indices)
            if (mutable_crosses[i].isSelected)
                return i
        return 0

    }
}
