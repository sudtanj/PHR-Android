/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 3/30/18 8:16 AM
 */

package sud_tanj.com.phr_android.CustomSensor;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Sensor.HardwareDriver.ArduinoUnoCH340;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 30/03/2018 - 8:16.
 * <p>
 * This class last modified by User
 */
public class newsensoractivity extends ArduinoUnoCH340 {
    private SensorData sensor = Global.getSensorGateway().getSensorData(this.getSensorId());

    @Override
    public void postDataReceived(String data) {
        HealthData healthData = new HealthData(sensor);
        healthData.setValues(data);
    }

    @Override
    public String getSensorId() {
        return "NewSensor148";
    }

    @Override
    public Boolean isScriptRunOnce() {
        return Boolean.FALSE;
    }
}
