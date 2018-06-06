/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 6/6/18 2:27 PM
 */

package sud_tanj.com.phr_android.CustomSensor;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Sensor.HardwareDriver.ArduinoUnoCH340;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 06/06/2018 - 14:27.
 * <p>
 * This class last modified by User
 */
public class AirQualitySensor extends ArduinoUnoCH340 {
    @Override
    public void postDataReceived(ArrayList<String> receivedDataInOneLoop) {
        if (this.isNumeric(receivedDataInOneLoop.get(0))) {
            HealthData healthData = new HealthData(getSensorData());
            healthData.addValues(receivedDataInOneLoop.get(0));
        }
    }

    @Override
    public Boolean isScriptRunOnce() {
        return Boolean.FALSE;
    }

    @Override
    public void analyzeData() {

    }
}
