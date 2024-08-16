package com.example.mystepcounter

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.mystepcounter.DataBase.Entity.StepCount
import com.example.mystepcounter.DataBase.ViewModel.MyViewModel
import com.example.mystepcounter.DataBase.ViewModel.ViewModelSingleton
import com.example.mystepcounter.Steps.OnPatDetectedListener
import java.text.SimpleDateFormat
import java.util.*

object DataManager {
    var mvm: MyViewModel
    private var stepCountList: List<StepCount> = emptyList()


    init {
        // Ensure the singleton is initialized
        try {
            mvm = ViewModelSingleton.getMyViewModel()
        } catch (e: IllegalStateException) {
            throw RuntimeException("ViewModelSingleton not initialized", e)
        }
    }
    fun getlist1(excludeCount: Int, callback: (List<StepCount>) -> Unit) {
        mvm.allUsers?.observeForever { stepCounts ->
            // Sort the list by date to find the most recent entries
            val sortedList = stepCounts.sortedByDescending { it.date }

            // Exclude the most recent 'excludeCount' entries
            val listWithoutLatest = if (sortedList.size > excludeCount) {
                sortedList.drop(excludeCount) // Exclude the most recent 'excludeCount' entries
            } else {
                emptyList()
            }

            callback(listWithoutLatest)
        }
    }


    fun getlist(callback: (List<StepCount>) -> Unit) {
        mvm.allUsers?.observeForever { stepCounts ->
            stepCounts?.let {
                stepCountList = it
                callback(it)
            }
        }
    }


    fun insertStepCount(stepCount: StepCount) {
        mvm.insertuser(stepCount)
    }

    fun updateStepCount(stepCount: StepCount) {
        mvm.updateUser(stepCount)
    }

    fun getAllUsers(): LiveData<MutableList<StepCount>>? {
        return mvm.allUsers
    }

    fun addStepsToDate(step: Int) {
        val formattedDate = getCurrentDate()
        mvm.getUserByDate(formattedDate)?.observeForever { stepCount ->
            if (stepCount != null) {
                val updatedStepCount = StepCount(formattedDate,  step) // Add new steps
                mvm.updateUser(updatedStepCount)
            } else {
                val newStepCount = StepCount(formattedDate, step)
                mvm.insertuser(newStepCount)
            }
        }
    }


    fun getCurrentDate(): String {
        val dateFormatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = Date()
        return dateFormatter.format(currentDate)
    }

    fun getTodayStep(lifecycleOwner: LifecycleOwner, callback: (Int) -> Unit) {
        val formattedDate = getCurrentDate()
        mvm.getUserByDate(formattedDate)?.observe(lifecycleOwner, Observer { stepCount ->
            val count = stepCount?.counter ?: 0
            Log.d("DataManager", "Today's step count: $count")
            callback(count)
        })
    }
    fun calculateCalories(stepCount: Int): Double {
        val caloriesPerStep = 0.04 // Average calories burned per step
        return stepCount * caloriesPerStep * (75 / 70.0) // Adjust for weight
    }
    fun calculateDistance(stepCount: Int, stepLengthInMeters: Double = 0.762): Double {
        return stepCount * stepLengthInMeters // Calculate distance in meters
    }


}
