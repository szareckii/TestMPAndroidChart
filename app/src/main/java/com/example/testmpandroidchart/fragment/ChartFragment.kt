package com.example.testmpandroidchart.fragment

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import androidx.core.util.Pair
import androidx.navigation.findNavController
import com.example.testmpandroidchart.R
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_chart.*
import java.util.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.android.synthetic.main.recycler_item_sensor.view.*
import java.text.SimpleDateFormat

class ChartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_chartFragment_to_dateFragment)
        }

        init()
        setLineChartData(10, 30)

        checkBoxTemp.setOnCheckedChangeListener { buttonView, isChecked ->
            val sets: List<ILineDataSet> = lineChart.data.dataSets
            val set = sets[0] as LineDataSet
            if (isChecked) {
                set.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.orange) }!!
            } else {
                set.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.transparent) }!!
            }
            lineChart.invalidate()
        }

        checkBoxHumidity.setOnCheckedChangeListener { buttonView, isChecked ->
            val sets: List<ILineDataSet> = lineChart.data.dataSets
            val set = sets[1] as LineDataSet
            if (isChecked) {
                set.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.blue) }!!
            } else {
                set.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.transparent) }!!
            }
            lineChart.invalidate()
        }
    }

    private fun init() {
        initDate()
    }
 
    private fun initDate() {
        val locale = Locale("ru", "RU")

        val sdfToday = SimpleDateFormat("dd MMM", locale)
        val sdfWeek = SimpleDateFormat("dd", locale)
        val sdfMonth = SimpleDateFormat("MMM", locale)

        val todayDate = Calendar.getInstance().time
        val currentDate = sdfToday.format(todayDate)

        val currentWeekDate = Calendar.getInstance()
        currentWeekDate.set(Calendar.DAY_OF_WEEK, currentWeekDate.firstDayOfWeek)
        val startWeek = sdfWeek.format(currentWeekDate.time)
        currentWeekDate.set(Calendar.DAY_OF_WEEK, currentWeekDate.firstDayOfWeek + 6)
        val endWeek = sdfWeek.format(currentWeekDate.time)

        val month = sdfMonth.format(currentWeekDate.time)

        todayTextView.text = currentDate
        weekTextView.text = ("$startWeek - $endWeek $month")
        monthTextView.text = month

        setCardsListener(currentDate, startWeek, endWeek, month)

        dateTextView.text = currentDate
    }

    private fun setCardsListener(
        currentDate: String,
        startWeek: String,
        endWeek: String,
        month: String
    ) {
        cardToday.setOnClickListener {
            cardToday.strokeColor = context?.getColor(R.color.light_blue_stroke)!!
            cardWeek.strokeColor = -1
            cardMonth.strokeColor = -1

            dateTextView.text = currentDate
            resetChart()
            setData((24).toInt(), 30)
        }

        cardWeek.setOnClickListener {
            cardWeek.strokeColor = context?.getColor(R.color.light_blue_stroke)!!
            cardToday.strokeColor = -1
            cardMonth.strokeColor = -1

            dateTextView.text = ("$startWeek - $endWeek $month")
            resetChart()
            setData((7).toInt(), 30)
        }

        cardMonth.setOnClickListener {
            cardMonth.strokeColor = context?.getColor(R.color.light_blue_stroke)!!
            cardToday.strokeColor = -1
            cardWeek.strokeColor = -1

            dateTextView.text = month
            resetChart()
            setData((30).toInt(), 30)
        }
    }

    private fun setLineChartData(count: Int, range: Int) {

        lineChart.description.isEnabled = false
        lineChart.setTouchEnabled(false)
        lineChart.isDragEnabled = false
        lineChart.setScaleEnabled(false)

        val leftAxisValues = ArrayList<String>()
        for (i in 1..60) {
            leftAxisValues.add("$i\u00B0")
        }

        val leftAxis = lineChart.axisLeft
        leftAxis.isEnabled = true
        leftAxis.setLabelCount(10, false)
        leftAxis.granularity = 1.0f
        leftAxis.isGranularityEnabled = true
        leftAxis.textSize = 14f
        leftAxis.valueFormatter = IndexAxisValueFormatter(leftAxisValues)
        leftAxis.axisMaximum = 40f
        leftAxis.axisMinimum = 0f

        leftAxis.textColor = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.orange) }!!
        leftAxis.setDrawGridLines(true)

        val rightAxisValues = ArrayList<String>()
        for (i in 1..100) {
            rightAxisValues.add("$i\u0025")
        }

        val rightAxis = lineChart.axisRight
        rightAxis.textColor = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.blue) }!!
        rightAxis.axisMaximum = 70f
        rightAxis.axisMinimum = 10f
        rightAxis.textSize = 14f
        rightAxis.setDrawGridLines(false)
        rightAxis.setDrawZeroLine(false)
        rightAxis.isGranularityEnabled = false
        rightAxis.valueFormatter = IndexAxisValueFormatter(rightAxisValues)

        val xAxisValues = ArrayList(
            listOf(
                "12ч",
                "13ч",
                "14ч",
                "15ч",
                "16ч",
                "17ч",
                "18ч",
                "19ч",
                "20ч",
                "21ч",
                "22ч",
                "23ч",
                "00ч"
            )
        )

        val xAxis = lineChart.xAxis
        xAxis.granularity = 1f
//        xAxis.setCenterAxisLabels(true)
        xAxis.isEnabled = true
        xAxis.setLabelCount(xAxisValues.size / 2, false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter(xAxisValues)
        xAxis.textSize = 14f

        lineChart.extraLeftOffset = 15F
        lineChart.extraRightOffset = 15F
        lineChart.extraBottomOffset = 15F
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false

        lineChart.setMaxVisibleValueCount(60)

        setData(count = count, range = range)

        val dateRangePicker =
            MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("Select dates")
                .setSelection(
                    Pair(
                        MaterialDatePicker.thisMonthInUtcMilliseconds(),
                        MaterialDatePicker.todayInUtcMilliseconds()
                    )
                )
                .build()

        calendarButtonDialog.setOnClickListener {
            dateRangePicker.show(childFragmentManager, "picker")
        }

        dateRangePicker.addOnPositiveButtonClickListener {
            datePickerListener(it)
        }

    }

    private fun setData(count: Int, range: Int) {
        val valuesTemp: ArrayList<Entry> = ArrayList()
        val valuesHumidity: ArrayList<Entry> = ArrayList()
        val valuesBoiler: ArrayList<Entry> = ArrayList()

        for (i in 0 until count) {
            val valueTemp = (0..range).random().toFloat()
            valuesTemp.add(Entry(i.toFloat(), valueTemp))
        }

        for (i in 0 until count) {
            val valueHumidity = (0..range).random().toFloat() + 20
            valuesHumidity.add(Entry(i.toFloat(), valueHumidity))
        }

        for (i in 0 until count) {
            val valueBoiler = (0..1).random().toFloat() * 50
            valuesBoiler.add(Entry(i.toFloat(), valueBoiler))
        }

        val set1: LineDataSet
        val set2: LineDataSet
        val set3: LineDataSet

        if (lineChart.data != null && lineChart.data.dataSetCount > 0) {
            set1 = lineChart.data.getDataSetByIndex(0) as LineDataSet
            set2 = lineChart.data.getDataSetByIndex(0) as LineDataSet
            set3 = lineChart.data.getDataSetByIndex(0) as LineDataSet

            set1.values = valuesTemp
            set2.values = valuesHumidity
            set3.values = valuesBoiler
            lineChart.data.notifyDataChanged()
            lineChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(valuesTemp, "Температура")
            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.orange) }!!
            if (!checkBoxTemp.isChecked)
                set1.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.transparent) }!!
            set1.setDrawCircles(false)
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.setDrawValues(false)
            set1.lineWidth = 3f

            set2 = LineDataSet(valuesHumidity, "Влажность")
            set2.axisDependency = YAxis.AxisDependency.RIGHT
            set2.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.blue) }!!
            if (!checkBoxHumidity.isChecked)
                set2.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.transparent) }!!
            set2.setDrawCircles(false)
            set2.mode = LineDataSet.Mode.CUBIC_BEZIER
            set2.setDrawValues(false)
            set2.lineWidth = 3f

            set3 = LineDataSet(valuesBoiler, "Котёл")
            set3.color = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.light_gray) }!!
            set3.fillColor = activity?.let { ContextCompat.getColor(it.applicationContext, R.color.green_boiler) }!!
            set3.setDrawCircles(false)
            set3.mode = LineDataSet.Mode.STEPPED
            set3.setDrawFilled(true)
            set3.setDrawValues(false)
            set3.lineWidth = 0f
        }

        val dataSets: ArrayList<ILineDataSet> = ArrayList()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)

        val data = LineData(dataSets)

        lineChart.data = data
        lineChart.invalidate()
    }

    private fun datePickerListener(it: Pair<Long, Long>) {
        val st = it.first
        val fin = it.second
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val calendarFin = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        calendar.timeInMillis = st
        calendarFin.timeInMillis = fin

        val diff = fin - st

        val seconds = diff / 1000
        val minutes = diff / (60 * 1000)
        val hours = minutes / 60
        val diffDays = diff / (24 * 60 * 60 * 1000)

        resetChart()
        setLineChartData((diffDays + 1).toInt(), 30)
    }

    private fun resetChart() {
        lineChart.fitScreen()
        lineChart.data?.clearValues()
        lineChart.xAxis.valueFormatter = null
        lineChart.notifyDataSetChanged()
        lineChart.clear()
        lineChart.invalidate()
    }
}