package com.example.mystepcounter.Activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.mystepcounter.DataManager
import com.example.mystepcounter.R
import com.example.mystepcounter.Steps.OnPatDetectedListener
import com.example.mystepcounter.Steps.PatDetector
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity(), OnPatDetectedListener {
    private lateinit var homeBTNGraph: MaterialButton
    private lateinit var text_step_count: TextView
    private lateinit var text_calorie_burned: TextView
    private lateinit var text_distance_travelled: TextView
    private val stepLengthInMeters = 0.762
    private var patDetector: PatDetector? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        findView()
        initView()
        DataManager.getTodayStep(this){ currentCount ->
            updateUI(currentCount)
        }
        patDetector = PatDetector(this, this)
        patDetector?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        patDetector?.stop() // Ensure to stop the detector
    }
    private fun findView() {
        homeBTNGraph = findViewById(R.id.home_BTN_graph)
        text_step_count = findViewById(R.id.text_step_count)
        text_calorie_burned = findViewById(R.id.text_calorie_burned)
        text_distance_travelled = findViewById(R.id.text_distance_travelled)
    }

    private fun initView() {
        homeBTNGraph.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onPatDetected(stepCount: Int) {
        DataManager.addStepsToDate(stepCount)
        runOnUiThread {
            updateUI(stepCount)
        }
    }

    private fun updateUI(currentCount: Int) {
        Log.d("MainActivity", "Updating UI with step count: $currentCount")
        text_step_count.text = currentCount.toString()
        text_calorie_burned.text = DataManager.calculateCalories(currentCount).toString()
        text_distance_travelled.text = DataManager.calculateDistance(currentCount, stepLengthInMeters).toString()
    }
}
