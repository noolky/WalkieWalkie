package com.example.walkiewalkie.stepActivity

import android.annotation.TargetApi
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.pm.PackageManager
import android.hardware.Sensor.TYPE_STEP_COUNTER
import android.hardware.Sensor.TYPE_STEP_DETECTOR
import android.hardware.SensorManager
import android.os.Build



class StepCountCheckUtil(private val mContext: Context) {

    //check sensor
    private var hasSensor: Boolean = isSupportStepCountSensor()


    @TargetApi(Build.VERSION_CODES.KITKAT)
    fun isSupportStepCountSensor(): Boolean {
        return mContext.packageManager
            .hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)
    }


    companion object{
        fun isSupportStepCountSensor(context: Context): Boolean {
            val sensorManager = context
                .getSystemService(SENSOR_SERVICE) as SensorManager
            val countSensor = sensorManager.getDefaultSensor(TYPE_STEP_COUNTER)
            val detectorSensor = sensorManager.getDefaultSensor(TYPE_STEP_DETECTOR)
            return countSensor != null || detectorSensor != null
        }
    }



}