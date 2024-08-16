package com.example.mystepcounter.Steps;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PatDetector implements SensorEventListener {

    private static final String TAG = "PatDetector";
    private SensorManager sensorManager;
    private Sensor stepSensor;
    private OnPatDetectedListener listener;
    private int maxStepCount = 0; // Track the maximum step count

    public PatDetector(Context context, OnPatDetectedListener listener) {
        this.listener = listener;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (stepSensor == null) {
            Log.e(TAG, "Step Counter Sensor not available.");
        }
    }

    public void start() {
        if (stepSensor != null) {
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "Sensor listener registered.");
        }
    }

    public void stop() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int currentStepCount = (int) event.values[0];

            // Check if the current step count is greater than the stored maximum
            if (currentStepCount > maxStepCount) {
                maxStepCount = currentStepCount;
                if (listener != null) {
                    listener.onPatDetected(maxStepCount);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }

    public static String getCurrentDate() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date currentDate = new Date();
        return dateFormatter.format(currentDate);
    }
}
