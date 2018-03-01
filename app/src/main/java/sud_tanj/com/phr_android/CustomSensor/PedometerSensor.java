/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 1/10/18 4:31 PM
 */

package sud_tanj.com.phr_android.CustomSensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import sud_tanj.com.phr_android.Custom.EmbeddedScript;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.HealthData;
import sud_tanj.com.phr_android.Database.SensorData;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 10/01/2018 - 16:31.
 * <p>
 * This class last modified by User
 */

public class PedometerSensor implements EmbeddedScript {
    private SensorData pedometer;
    private String sensorId="pedometer03102";

    @Override
    public Boolean run() {
        pedometer=Global.getSensorGateway().getSensorData(sensorId);
        SensorManager sensorManager = (SensorManager) Global.getContext().getSystemService(Context.SENSOR_SERVICE);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            sensorManager.registerListener(new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    pedometer.addHealthData(new HealthData(pedometer,String.valueOf(sensorEvent.values[0])));
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int i) {

                }
            }, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        return true;
    }

    @Override
    public String getSensorId() {
        return sensorId;
    }
}
