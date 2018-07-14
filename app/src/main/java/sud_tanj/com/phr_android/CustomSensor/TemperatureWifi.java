/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 7/14/18 12:18 PM
 */

package sud_tanj.com.phr_android.CustomSensor;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Sensor.HardwareDriver.LolinESP8266;

/**
 * This class is part of PHRAndroid Project
 * Any modified within this class without reading the
 * manual will cause problem!
 * <p>
 * Created by Sudono Tanjung on 14/07/2018 - 12:18.
 * <p>
 * This class last modified by User
 */
public class TemperatureWifi extends LolinESP8266 {
    @Override
    public void postDataReceived(ArrayList<String> receivedDataInOneLoop) {
        super.postDataReceived(receivedDataInOneLoop);
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
