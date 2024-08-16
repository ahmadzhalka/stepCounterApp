package com.example.mystepcounter.Activitys

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import com.example.mystepcounter.DataBase.Entity.StepCount
import com.example.mystepcounter.DataManager
import com.example.mystepcounter.R
import com.example.mystepcounter.Steps.OnPatDetectedListener
import com.example.mystepcounter.Steps.PatDetector
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class GraphActivity : AppCompatActivity(), OnPatDetectedListener {
    private lateinit var text: TextView
    private lateinit var barChart: BarChart
    private lateinit var btn_back: ImageButton
    private var stepCountList: List<StepCount> = emptyList()
    private var patDetector: PatDetector? = null
    private var excludeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
        findView()
        initView()
        setupChart()
        loadData()
        updateUI()
        patDetector = PatDetector(this, this)
        patDetector?.start()
    }

    private fun findView() {
        barChart = findViewById(R.id.barChart)
        btn_back = findViewById(R.id.btn_back)
        text = findViewById(R.id.text)
    }

    private fun initView() {
        btn_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setupChart() {
        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f

        val leftAxis: YAxis = barChart.axisLeft
        leftAxis.setDrawGridLines(true)
        leftAxis.axisMinimum = 0f

        barChart.axisRight.isEnabled = false
        barChart.description.isEnabled = false
        barChart.legend.isEnabled = true
    }

    private fun setData() {
        if (stepCountList.isEmpty()) {
            Log.d("GraphActivity", "No data available to display")
            barChart.clear() // Clear the chart if no data
            return
        }

        val values = ArrayList<BarEntry>()
        val xAxisLabels = ArrayList<String>()

        for ((index, stepCount) in stepCountList.withIndex()) {
            values.add(BarEntry(index.toFloat(), stepCount.counter.toFloat()))
            xAxisLabels.add(stepCount.date)
        }

        val dataSet = BarDataSet(values, "Step Counts")
        dataSet.color = resources.getColor(R.color.black, theme)
        dataSet.valueTextColor = resources.getColor(R.color.black, theme)
        dataSet.valueTextSize = 12f

        val barData = BarData(dataSet)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(xAxisLabels)
        barChart.data = barData
        barChart.invalidate() // Refresh the chart
    }

    private fun loadData() {
        DataManager.getAllUsers()?.observe(this, Observer { stepCounts ->
            excludeCount = stepCounts.size
            Log.d("GraphActivity", "Number of records: $excludeCount")
                DataManager.getlist{ updatedStepCounts ->
                    stepCountList = updatedStepCounts
                    setData() // Update chart with the new data
                }

        })
    }



    override fun onPatDetected(stepCount: Int) {
        DataManager.addStepsToDate(stepCount)
        updateUI()
    }

    private fun updateUI() {
        DataManager.getAllUsers()?.observe(this, Observer { stepCounts ->
            excludeCount = stepCounts.size
            Log.d("GraphActivity", "Number of records: $excludeCount")
            DataManager.getlist{ updatedStepCounts ->
                stepCountList = updatedStepCounts
                setData() // Update chart with the new data
            }

        })
    }
}
