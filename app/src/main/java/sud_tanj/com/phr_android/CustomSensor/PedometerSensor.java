/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/10/18 4:31 PM
 */

package sud_tanj.com.phr_android.CustomSensor;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Data.HealthDataAnalysis;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Sensor.SensorListener;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 10/01/2018 - 16:31.
 * <p>
 * This class last modified by User
 */

public class PedometerSensor extends SensorListener {
    private PackageManager packageManager=null;
    private SensorEvent sensorEventPedometer=null;

    @Override
    protected void syncData() {
        super.syncData();
        if(this.packageManager==null) {
            this.packageManager = Global.getContext().getPackageManager();
            if (packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_STEP_COUNTER)) {
                SensorManager sensorManager = (SensorManager) Global.getContext().getSystemService(Context.SENSOR_SERVICE);
                Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
                if (countSensor != null) {
                    sensorManager.registerListener(new SensorEventListener() {
                        @Override
                        public void onSensorChanged(SensorEvent sensorEvent) {
                            sensorEventPedometer=sensorEvent;

                            HealthData healthData = new HealthData(getSensorData());
                            healthData.addValues(String.valueOf(sensorEventPedometer.values[0]));
                        }

                        @Override
                        public void onAccuracyChanged(Sensor sensor, int i) {

                        }
                    }, countSensor, SensorManager.SENSOR_DELAY_UI);
                }
            }
        }
    }

    @Override
    public Boolean isScriptRunOnce() {
        return Boolean.TRUE;
    }

    @Override
    public void analyzeData(ArrayList<String> healthData) {
        //HealthDataAnalysis temp=new HealthDataAnalysis(getSensorData());
        //temp.setAnalysis("This is a test analysis");
    }
}
