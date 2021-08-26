package com.example.testmpandroidchart.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.example.testmpandroidchart.R
import com.example.testmpandroidchart.source.Data
import com.example.testmpandroidchart.source.StatusSensorEnum
import com.example.testmpandroidchart.source.TypeSensorEnum
import kotlinx.android.synthetic.main.recycler_item_sensor.view.*
import kotlinx.android.synthetic.main.recycler_item_temp.view.*
import kotlinx.android.synthetic.main.recycler_item_temp.view.battery
import kotlinx.android.synthetic.main.recycler_item_temp.view.labelPlaceTextView

class SensorAdapter(private var onListItemClickListener: OnListItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: List<Data> = mutableListOf()

    fun setData(data: List<Data>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
                TYPE_TEMP -> TempViewHolder(
                    inflater.inflate(R.layout.recycler_item_temp, parent, false) as View
                    )
                TYPE_SENSOR ->
                    SensorViewHolder(
                        inflater.inflate(R.layout.recycler_item_sensor, parent, false) as View
                    )
                else ->
                    BoilerViewHolder(
                        inflater.inflate(R.layout.recycler_item_boiler, parent, false) as View
                    )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)) {
            TYPE_TEMP -> {
                holder as TempViewHolder
                holder.bind(data[position])
            }
            TYPE_SENSOR -> {
                holder as SensorViewHolder
                holder.bind(data[position])
            }
            else -> {
                holder as BoilerViewHolder
                holder.bind(data[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (data[position].type) {
                    TypeSensorEnum.TEMP -> TYPE_TEMP
                    TypeSensorEnum.BOILER -> TYPE_BOILER
                    else -> TYPE_SENSOR
        }
    }

    inner class TempViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.labelPlaceTextView.text = data.labelPlace
                itemView.tempTextView.text = itemView.context.getString(R.string.celsius, data.temp)
                itemView.humidityTextView.text = itemView.context.getString(R.string.humidity, data.humidity)
                itemView.battery.setLevel(data.battery)

                itemView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class SensorViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.labelPlaceTextView.text = data.labelPlace
                itemView.labelSensorTextView.text = data.type.typeSensor
                itemView.statusSensorTextView.text = data.status?.statusSensor

                when (data.status?.statusSensor) {

                    StatusSensorEnum.ENABLED.statusSensor -> {
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.light_blue))
                        itemView.cardView.strokeColor = itemView.context.getColor(R.color.light_blue_stroke)
                        itemView.cardView.typeSensorImageView.setImageDrawable(
                            when (data.type) {
                                TypeSensorEnum.WATER -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_blue)
//                                TypeSensorEnum.BOILER -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_boiler_blue)
//                                TypeSensorEnum.WINDOW -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_blue)
                                else -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_blue)
                            }
                        )
                        itemView.cardView.statusSensorTextView.setTextColor(itemView.context.getColor(R.color.blue))
                    }

                    StatusSensorEnum.DISABLE.statusSensor ->
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white))

                    StatusSensorEnum.ALERT.statusSensor -> {
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.light_red))
                        itemView.cardView.strokeColor = itemView.context.getColor(R.color.light_red_stroke)
                        itemView.cardView.typeSensorImageView.setImageDrawable(
                            when (data.type) {
                                TypeSensorEnum.WATER -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_red)
//                                TypeSensorEnum.BOILER -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_boiler_red)
//                                TypeSensorEnum.WINDOW -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_red)
                                else -> AppCompatResources.getDrawable(itemView.context, R.drawable.ic_water_red)
                            }
                        )
                        itemView.cardView.statusSensorTextView.setTextColor(itemView.context.getColor(R.color.red))
                    }
                }
                itemView.battery.setLevel(data.battery)

                itemView.setOnClickListener {
                    onListItemClickListener.onItemClick(data)
                }
            }
        }
    }

    inner class BoilerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Data) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.labelPlaceTextView.text = data.labelPlace
                itemView.labelSensorTextView.text = data.type.typeSensor
                itemView.statusSensorTextView.text = data.status?.statusSensor

                when (data.status?.statusSensor) {

                    StatusSensorEnum.ENABLED.statusSensor -> {
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.light_blue))
                        itemView.cardView.strokeColor = itemView.context.getColor(R.color.light_blue_stroke)
                        itemView.cardView.typeSensorImageView.setImageDrawable(
                            AppCompatResources.getDrawable(itemView.context, R.drawable.ic_boiler_blue)
                        )
                        itemView.cardView.statusSensorTextView.setTextColor(itemView.context.getColor(R.color.blue))
                    }

                    StatusSensorEnum.DISABLE.statusSensor ->
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.white))

                    StatusSensorEnum.ALERT.statusSensor -> {
                        itemView.cardView.setCardBackgroundColor(itemView.context.getColor(R.color.light_red))
                        itemView.cardView.strokeColor = itemView.context.getColor(R.color.light_red_stroke)
                        itemView.cardView.typeSensorImageView.setImageDrawable(
                            AppCompatResources.getDrawable(itemView.context, R.drawable.ic_boiler_red)
                        )
                        itemView.cardView.statusSensorTextView.setTextColor(itemView.context.getColor(R.color.red))
                    }
                }
//                itemView.setOnClickListener {
//                    onListItemClickListener.onItemClick(data)
//                }
            }
        }
    }

    interface OnListItemClickListener {
        fun onItemClick(listItemData: Data)
    }

    companion object {
        private const val TYPE_TEMP = 0
        private const val TYPE_SENSOR = 1
        private const val TYPE_BOILER = 2
    }
}