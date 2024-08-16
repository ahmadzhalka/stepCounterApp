package com.example.mystepcounter.Activitys

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.mystepcounter.DataBase.Entity.StepCount
import com.example.mystepcounter.DataManager
import com.example.mystepcounter.R
import com.example.mystepcounter.Service.MyDB
import com.example.mystepcounter.Service.StepCounterService
import com.example.mystepcounter.Steps.PatDetector

import com.google.android.material.button.MaterialButton
import java.util.Date

class LoginActivity : AppCompatActivity() {
    private lateinit var button_continue: MaterialButton
    private lateinit var patDetector: PatDetector

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
       // Create an instance of StepCount with the current date and counter
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            when {
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED -> {
                    setupPatDetector()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.ACTIVITY_RECOGNITION)
                }
            }
        } else {
            setupPatDetector()
        }
      /* DataManager.getAllStepCounts { stepCounts ->
           val stepCountsArrayList = ArrayList(stepCounts)
           // Use the stepCountsArrayList as needed
           println("Step counts: $stepCountsArrayList")
       }*/
        findView()
        initView()
       // startService()
   }

    private fun  findView(){
        button_continue=findViewById(R.id.button_continue)
    }
    private fun initView(){
        button_continue.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startService()
            startActivity(intent)
        }

    }
  private fun setupPatDetector() {
        patDetector = PatDetector(this) { stepCount ->
            // Handle step count here
            Toast.makeText(this, "Steps: $stepCount", Toast.LENGTH_SHORT).show()
        }
        patDetector.start()
    }
    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                setupPatDetector()
            } else {
                Toast.makeText(this, "Permission denied to access activity recognition", Toast.LENGTH_SHORT).show()
            }
        }
    private fun startService() {
        MyDB.saveState(this, true)
        sendActionToService(StepCounterService.START_FOREGROUND_SERVICE)
    }

    private fun sendActionToService(action: String) {
        val intent = Intent(this, StepCounterService::class.java).apply {
            this.action = action
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}