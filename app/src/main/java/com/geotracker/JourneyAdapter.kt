package com.geotracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.geotracker.database.Journey
import com.geotracker.databinding.ItemJourneyBinding
import java.text.SimpleDateFormat
import java.util.*

class JourneyAdapter(private val onClick: (Journey) -> Unit) :
    ListAdapter<Journey, JourneyAdapter.VH>(DIFF) {

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Journey>() {
            override fun areItemsTheSame(a: Journey, b: Journey) = a.id == b.id
            override fun areContentsTheSame(a: Journey, b: Journey) = a == b
        }
    }

    inner class VH(val b: ItemJourneyBinding) : RecyclerView.ViewHolder(b.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemJourneyBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: VH, position: Int) {
        val j   = getItem(position)
        val sdf = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        val dur = if (j.endTime > 0) j.endTime - j.startTime else 0L
        val h   = dur / 3600000
        val m   = (dur % 3600000) / 60000
        val s   = (dur % 60000) / 1000

        with(holder.b) {
            tvName.text     = j.name
            tvDate.text     = sdf.format(Date(j.startTime))
            tvDistance.text = "%.2f km".format(j.totalDistance / 1000f)
            tvDuration.text = "%02d:%02d:%02d".format(h, m, s)
            tvAvgSpeed.text = "%.1f km/h".format(j.avgSpeed * 3.6f)
            tvMaxSpeed.text = "%.1f km/h".format(j.maxSpeed * 3.6f)
        }
        holder.itemView.setOnClickListener { onClick(j) }
    }
}
