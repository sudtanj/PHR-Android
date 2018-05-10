/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/7/18 8:31 PM
 */

package sud_tanj.com.phr_android.Database.Sensor;

import sud_tanj.com.phr_android.Interface.Sensor.EmbeddedScript;

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
    private String name = null;
    private EmbeddedScript script = null;
    private SensorData sensorData = null;
    private Boolean scriptExist;

    public SensorEmbeddedScript(String name, SensorData sensorData) {
        this.name = name;
        this.sensorData = sensorData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void findScript() {
        try {
            Class listener = Class.forName("sud_tanj.com.phr_android.CustomSensor." + name);
            script = (EmbeddedScript) (listener.newInstance());
            this.scriptExist = true;
        } catch (Exception e) {
            this.scriptExist = false;
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
