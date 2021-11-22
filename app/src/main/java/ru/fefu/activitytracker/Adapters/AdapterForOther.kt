package ru.fefu.activitytracker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.Items.CrossItem
import ru.fefu.activitytracker.R

class AdapterForOther(crosses: List<CrossItem>, is_my: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val distance: TextView = itemView.findViewById(R.id.activity_date_out)
        private val time: TextView = itemView.findViewById(R.id.activity_time)
        private val type: TextView = itemView.findViewById(R.id.activity_type)
        private val beneficial: TextView = itemView.findViewById(R.id.activity_beneficial)
        private val date: TextView = itemView.findViewById(R.id.activity_date)

        init {
            itemView.setOnClickListener(){
                val position =absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }
        fun bind(cross_item: CrossItem) {
            distance.text = cross_item.distance
            time.text = cross_item.time
            type.text = cross_item.type
            beneficial.text = cross_item.beneficial
            date.text = cross_item.date
        }
    }

    inner class RecyclerViewHolderForDate(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val time: TextView = itemView.findViewById(R.id.activity_date_out)
        fun bind(cross_item: CrossItem) {
            time.text = cross_item.time
        }
    }
    private var itemClickListener:  (Int)-> Unit= {}
    private val is_my = is_my
    private var is_date = false;
    private val mutable_crosses = crosses.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (!is_date) {
            val veiw = LayoutInflater.from(parent.context)
                .inflate(R.layout.number_cross_list, parent, false)
            return RecyclerViewHolder(veiw)
        }
        val veiw = LayoutInflater.from(parent.context)
            .inflate(R.layout.number_cross_list_date, parent, false)
        return RecyclerViewHolderForDate(veiw)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (is_date) {
            (holder as RecyclerViewHolderForDate).bind(mutable_crosses[position])
        } else {
            (holder as RecyclerViewHolder).bind(mutable_crosses[position])
        }
    }

    override fun getItemCount(): Int = mutable_crosses.size

    override fun getItemViewType(position: Int): Int {
        if (is_my) {
            is_date = position % 2 == 0
        } else
            is_date = position == 0
        return super.getItemViewType(position)
    }

    fun setItemClickListener(listener:(Int)->Unit){
        itemClickListener = listener
    }

}