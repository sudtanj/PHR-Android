/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by Sudono Tanjung on 6/6/18 9:25 AM
 */

package sud_tanj.com.phr_android.CustomSensor;

import java.util.ArrayList;

import sud_tanj.com.phr_android.Database.Data.HealthData;
import sud_tanj.com.phr_android.Sensor.HardwareDriver.ArduinoUnoCH340;

/**
 * Created by Sudono Tanjung on 6/6/2018.
 */

public class airquality extends ArduinoUnoCH340 {
    @Override
    public void postDataReceived(ArrayList<String> receivedDataInOneLoop) {
        HealthData healthData = new HealthData(getSensorData());
        healthData.addValues("200");
        if (this.isNumeric(receivedDataInOneLoop.get(0))) {
        //    HealthData healthData = new HealthData(getSensorData());
          //  healthData.addValues(receivedDataInOneLoop.get(0));
        }
    }

    @Override
    public Boolean isScriptRunOnce() {
        return Boolean.FALSE;
    }

    @Override
    public void analyzeData(ArrayList<String> healthData) {

    }
}
