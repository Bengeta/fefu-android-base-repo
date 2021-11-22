package ru.fefu.activitytracker.Adapters

import android.location.Location
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.fefu.activitytracker.DataBaseStaff.App
import ru.fefu.activitytracker.DataBaseStaff.CrossItemEntity
import ru.fefu.activitytracker.DataBaseStaff.SerialiseClass
import ru.fefu.activitytracker.Enums.CrossType
import ru.fefu.activitytracker.Items.CrossItem
import ru.fefu.activitytracker.R
import java.lang.Math.PI
import java.util.*
import kotlin.math.roundToLong

class RecyclerAdapter(crosses: List<CrossItemEntity>, is_my: Boolean) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val distance: TextView = itemView.findViewById(R.id.activity_date_out)
        private val time: TextView = itemView.findViewById(R.id.activity_time)
        private val type: TextView = itemView.findViewById(R.id.activity_type)
        private val date: TextView = itemView.findViewById(R.id.activity_date)

        init {
            itemView.setOnClickListener() {
                val position = absoluteAdapterPosition
                itemClickListener.invoke(position)
            }
        }

        fun bind(cross_item: CrossItemEntity) {
            distance.text = countDistance(SerialiseClass().listDecode(cross_item.coordinates!!))
            time.text = countPeriod(cross_item.date_end!! - cross_item.date_start!!)
            type.text = CrossType.values()[cross_item.type!!].type
            date.text = DateFormat.format("dd.MM.yyyy", Date(cross_item.date_start!!)).toString()

        }
    }

    inner class RecyclerViewHolderForDate(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val time: TextView = itemView.findViewById(R.id.activity_date_out)
        fun bind(cross_item: CrossItemEntity) {
            time.text = cross_item.date
        }
    }

    private var itemClickListener: (Int) -> Unit = {}
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
        is_date = mutable_crosses[position].date != null
        return super.getItemViewType(position)
    }

    fun setItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }

    private fun countPeriod(period: Long): String {
        val cal1: Calendar = Calendar.getInstance()
        cal1.time = Date(period)
        var period_str: String = "";
        val hour = cal1.get(Calendar.HOUR)
        val minute = cal1.get(Calendar.MINUTE)
        if (hour > 0)
            period_str = period_str + cal1.get(Calendar.HOUR) + " час(ов)"
        if (minute > 0)
            period_str = period_str + cal1.get(Calendar.MINUTE) + " минут(а)"
        if (period_str == "") period_str = "Меньше минуты"
        return period_str
    }

    private fun countDistance(list: List<Pair<Double, Double>>): String {
        var distance: Double = 0.0

        for (i in 0..list.size - 2) {
            val startPoint = Location("locationA")
            startPoint.latitude = list[i].first
            startPoint.longitude = list[i].second

            val endPoint = Location("locationA")
            endPoint.latitude = list[i + 1].first
            endPoint.longitude = list[i + 1].second

            distance += startPoint.distanceTo(endPoint).toDouble()/1000
        }// first - latitude second - longitude
        return distance.roundToLong().toString() +" km"
    }

}