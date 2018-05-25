/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 8:31 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import com.google.firebase.database.DatabaseReference;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorListener.EmbeddedScriptListener;
import sud_tanj.com.phr_android.FirebaseCommunicator.RealTimeDatabase.SensorSynchronizer;
import sud_tanj.com.phr_android.Sensor.Interface.EmbeddedScript;
import sud_tanj.com.phr_android.Sensor.SensorListener;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 07/05/2018 - 20:29.
 * <p>
 * This class last modified by User
 */
public class SensorEmbeddedScript implements Runnable {
    public static final String SENSOR_DATA_CHILD_NAME = "sensor";
    private String name = null;
    private SensorListener script = null;
    private SensorData sensorData = null;
    private Boolean scriptExist;
    private SensorSynchronizer sensorInformation;
    private DatabaseReference dataReference = null;

    public SensorEmbeddedScript(String name, SensorData sensorData) {
        this.name = name;
        this.sensorData = sensorData;
        this.dataReference= Global.getMainDatabase().child(SENSOR_DATA_CHILD_NAME).child(sensorData.getSensorInformation().getSensorId());

        sensorInformation = new SensorSynchronizer(this.dataReference, sensorData);

        sensorInformation.add(new EmbeddedScriptListener(),"EmbeddedScript");

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.sensorInformation.changeVariable(name);
    }

    private void findScript() {
        if(this.script==null) {
            try {
                if(!name.isEmpty()) {
                    Class listener = Class.forName("sud_tanj.com.phr_android.CustomSensor." + name);
                    this.script = (SensorListener) (listener.newInstance());
                    System.out.println(this.script);
                    this.script.setSensorData(this.sensorData);
                    this.scriptExist = true;
                }
                else {
                    this.scriptExist=false;
                }
            } catch (Exception e) {
                this.scriptExist = false;
            }
        }
    }

    private Boolean isScriptExist() {
        findScript();
        return scriptExist;
    }

    @Override
    public void run() {
        if (sensorData.getSensorInformation().isSensorActive()) {
            if (isScriptExist()) {
                this.script.run();
            }
        }
    }
}
